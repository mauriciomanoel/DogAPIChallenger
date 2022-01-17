package com.mauricio.dogapichallenger.utils

import android.content.Context
import com.google.gson.Gson
import java.lang.reflect.Type

object SharedPreferencesUtils {

    @JvmStatic
    fun save(context: Context, objectReceived: Any, key: String) {
        val mPrefs = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val json = Gson().toJson(objectReceived)
        mPrefs.edit()
            .putString(key, json)
            .commit()
    }

    @JvmStatic
    fun  get(context: Context, type: Type, key: String): Any? {
        var valuePreference: Any? = context.getSharedPreferences(key, Context.MODE_PRIVATE)?.let { mPrefs ->
            mPrefs.getString(key, null)?.let { mJsonValue ->
                Gson().fromJson(mJsonValue, type)
            }
        }

        return valuePreference
    }
}