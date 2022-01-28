package com.mauricio.dogbreedapi.breeds.repository

import androidx.room.TypeConverter
import com.mauricio.dogbreedapi.breeds.models.Eight
import com.mauricio.dogbreedapi.breeds.models.Image
import com.mauricio.dogbreedapi.utils.extensions.fromGson
import com.mauricio.dogbreedapi.utils.extensions.toGson

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
