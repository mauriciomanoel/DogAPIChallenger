package com.mauricio.dogapichallenger.utils

import android.content.Context
import com.google.gson.Gson
import java.lang.reflect.Type

object SharedPreferencesUtils {

    @JvmStatic
    fun save(context: Context, objectReceived: Any, key: String) {
        try {
            val mPrefs = context.getSharedPreferences(key, Context.MODE_PRIVATE)
            val json = Gson().toJson(objectReceived)
            mPrefs.edit()
                .putString(key, json)
                .commit()
        } catch (e: Exception) {}
    }

    @JvmStatic
    fun get(context: Context, type: Type, key: String): Any? {
        var valuePreference: Any? = null
        try {
            context.getSharedPreferences(key, Context.MODE_PRIVATE)?.let { mPrefs ->
                mPrefs.getString(key, null)?.let { mJsonValue ->
                    valuePreference = Gson().fromJson(mJsonValue, type)
                }
            }
        } catch (e: Exception) {}

        return valuePreference
    }
}