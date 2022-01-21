package com.mauricio.dogapichallenger.breeds.repository

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mauricio.dogapichallenger.breeds.models.Eight
import com.mauricio.dogapichallenger.breeds.models.Image

class Converters {

    companion object {

        @TypeConverter
        @JvmStatic
        fun toString(value: Image?) = value?.toGson()

        @TypeConverter
        @JvmStatic
        fun toString(value: Eight?) = value?.toGson()


        @TypeConverter
        @JvmStatic
        fun toListOfObjects1(value: String?): Image? {
            return value?.fromGson(Image::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun toListOfObjects(value1: String?): Eight? {
            return value1?.fromGson(Eight::class.java)
        }

    }


}

fun <T> String.fromGson(classOfT: Class<T>) = Gson().fromJson(this, classOfT)

fun Any.toGson() = Gson().toJson(this)