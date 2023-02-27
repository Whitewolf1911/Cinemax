package com.alibasoglu.cinemax.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun dateFormatter(dateInString: String): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val formatterOut = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    try {
        val date = formatter.parse(dateInString)
        return formatterOut.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return dateInString
}
