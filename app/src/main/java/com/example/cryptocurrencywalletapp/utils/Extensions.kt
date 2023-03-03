package com.example.cryptocurrencywalletapp.utils

import android.text.Editable
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


fun View.setGone() {
    this.visibility = View.GONE
}
fun View.setVisible() {
    this.visibility = View.VISIBLE
}
fun BottomNavigationView.setGone() {
    this.visibility = View.GONE
}
fun BottomNavigationView.setVisible() {
    this.visibility = View.VISIBLE
}

fun Long.toDateTime(): String? {
    return try {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.UK)
        val backToDate = Date(this)
        sdf.format(backToDate)
    } catch (e: Exception) {
        e.toString()
    }
}

fun String.toDateTime(): Long{
    return try {
       Timestamp.valueOf(this).time

    }catch (e: Exception) {
        e.printStackTrace()
        0L
    }
}
fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)


