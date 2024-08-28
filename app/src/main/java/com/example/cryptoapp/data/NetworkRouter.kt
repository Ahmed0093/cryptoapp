package com.example.cryptoapp.data

import com.example.cryptoapp.domain.BaseException
import com.example.cryptoapp.domain.ClientException
import com.example.cryptoapp.domain.NoConnectionException
import com.example.cryptoapp.domain.UnAuthorizedException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.Response
import java.io.InterruptedIOException
import java.net.HttpURLConnection

object NetworkRouter {

    suspend fun <T : Any> invokeApi(
        call: suspend () -> Response<T>,
    ): T {
        var response: Response<T>? = null

        try {
            response = call()
            val responseModel = response.body()
            if (response.isSuccessful && responseModel != null) {
                return responseModel
            }
            val responseErrorBody = response.errorBody()
            var responseErrorModel = ResponseErrorModel()
            if (responseErrorBody != null)
                responseErrorModel = parseError(responseErrorBody) ?: ResponseErrorModel()
            throw when (response.code()) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    UnAuthorizedException()
                }

                else -> {
                    ClientException(message = responseErrorModel.error)
                }
            }
        } catch (e: IOException) {
            throw NoConnectionException()
        } catch (e: InterruptedIOException) {
            throw InterruptedIOException()
        } catch (e: BaseException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}


data class ResponseErrorModel(
    val error: String? = "some thing went Wrong",
)

private fun parseError(errorBody: ResponseBody?): ResponseErrorModel? {
    return try {
        val gson = Gson()
        val type = object : TypeToken<ResponseErrorModel>() {}.type
        val errorResponseModel: ResponseErrorModel =
            gson.fromJson(errorBody?.charStream(), type)
        errorResponseModel
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}