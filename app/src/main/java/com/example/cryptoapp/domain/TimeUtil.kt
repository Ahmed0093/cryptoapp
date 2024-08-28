package com.example.cryptoapp.domain

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun convertToDateTimeString(dateTimeString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())//SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date: Date = inputFormat.parse(dateTimeString) ?: return ""

    val outputFormat = SimpleDateFormat("yyyy-MM-dd hh:mm a")
    val formattedDate = outputFormat.format(date)
    return formattedDate
}
fun convertToDateString(dateTimeString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())//SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date: Date = inputFormat.parse(dateTimeString) ?: return ""

    val outputFormat = SimpleDateFormat("yyyy-MM-dd")
    val formattedDate = outputFormat.format(date)
    return formattedDate
}


