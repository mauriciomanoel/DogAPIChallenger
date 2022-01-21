package com.mauricio.dogapichallenger.breeds.repository

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mauricio.dogapichallenger.breeds.models.Eight
import com.mauricio.dogapichallenger.breeds.models.Image

class ConvertersImage {

    companion object {
        @TypeConverter
        @JvmStatic
        fun toImage(value: String): Eight {
            val type = object : TypeToken<Image>() {}.type
            return Gson().fromJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromImage(value: Image): String {
            return Gson().toJson(value)
        }
    }
}