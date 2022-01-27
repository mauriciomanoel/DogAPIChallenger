package com.mauricio.dogapichallenger.utils.extensions

import android.content.Context
import com.google.gson.Gson
import com.mauricio.dogapichallenger.R

fun <T> String.fromGson(classOfT: Class<T>) = Gson().fromJson(this, classOfT)

fun Any.toGson() = Gson().toJson(this)

fun String.checkIsEmpty(context: Context): String {
    if (this.isNullOrBlank()) return context.getString(R.string.is_not_available)
    return this
}