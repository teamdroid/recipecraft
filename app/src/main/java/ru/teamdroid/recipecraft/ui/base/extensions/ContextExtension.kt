package ru.teamdroid.recipecraft.ui.base.extensions

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

fun Context.getPrivateSharedPreferences(): SharedPreferences {
    return this.getSharedPreferences(packageName + "_preference", Context.MODE_PRIVATE)
}

fun Context.setProperty(tag: String, value: Int) {
    try {
        val spe = getPrivateSharedPreferences().edit()
        spe.putInt(tag, value).apply()
        Log.d(this.javaClass.simpleName, "setProperty: $value  $tag")
    } catch (exception: Exception) {
        Log.e(this.javaClass.simpleName, exception.toString())
    }
}

fun Context.getProperty(tag: String, default_value: Int): Int {
    var result = default_value
    try {
        val sp = getPrivateSharedPreferences()
        result = sp.getInt(tag, default_value)
        Log.d(this.javaClass.simpleName, "getProperty: $result  $tag")
    } catch (exception: Exception) {
        Log.e(this.javaClass.simpleName, exception.toString())
    }
    return result
}