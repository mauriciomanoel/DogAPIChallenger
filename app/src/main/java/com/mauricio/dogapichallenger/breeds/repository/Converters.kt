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
        fun toListOfObjectsImage(valueImage: String?): Image? {
            return valueImage?.fromGson(Image::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun toListOfObjectsEight(valueEight: String?): Eight? {
            return valueEight?.fromGson(Eight::class.java)
        }
    }
}

fun <T> String.fromGson(classOfT: Class<T>) = Gson().fromJson(this, classOfT)

fun Any.toGson() = Gson().toJson(this)