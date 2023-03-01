package com.example.cryptocurrencywalletapp.utils

import android.R
import android.content.Context
import android.content.res.Resources
import android.text.Editable
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


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
fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)


