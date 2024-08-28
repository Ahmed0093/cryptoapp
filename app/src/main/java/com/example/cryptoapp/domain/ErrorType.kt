package com.example.cryptoapp.domain

open class BaseException(var title: String? = null, var errorType: String? = null, var errorCode: String? = null) : Exception()
open class BaseCustomException(override val message: String?) : BaseException()
class NoConnectionException : BaseException()
class UnAuthorizedException : BaseException()

open class ClientException(var code: String? = null, override var message: String? = null) :
    BaseCustomException(message)