package com.mauricio.dogapichallenger.utils

import android.content.Context
import com.mauricio.dogapichallenger.R

object TextUtils {

    @JvmStatic
    fun checkIsEmpty(context: Context, value: String?): String {
        if (value.isNullOrBlank()) return context.getString(R.string.is_not_available)
        return value
    }
}