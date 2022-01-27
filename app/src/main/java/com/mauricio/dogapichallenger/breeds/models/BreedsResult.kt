package com.mauricio.dogapichallenger.breeds.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName

typealias BreedsResult = ArrayList<Breed>
typealias BreedsByIdResult = ArrayList<BreedResultElement>

data class BreedResultElement (
    val breeds: List<Breed>,
    val height: Long,
    val id: String,
    val url: String,
    val width: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Breed) ?: listOf(),
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(breeds)
        parcel.writeLong(height)
        parcel.writeString(id)
        parcel.writeString(url)
        parcel.writeLong(width)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BreedResultElement> {
        override fun createFromParcel(parcel: Parcel): BreedResultElement {
            return BreedResultElement(parcel)
        }

        override fun newArray(size: Int): Array<BreedResultElement?> {
            return arrayOfNulls(size)
        }
    }
}

@Entity(tableName="Breed")
data class Breed (
    @SerializedName("bred_for")
    val bredFor: String? = null,
    @SerializedName("breed_group")
    val breedGroup: String? = null,
    val height: Eight? = null,
    @PrimaryKey @ColumnInfo(name = "id")
    val id: Long,
    val image: Image? = null,
    val lifeSpan: String? = "",
    val name: String,
    val origin: String? = "",
    @SerializedName("reference_image_id")
    val referenceImageID: String? = null,
    val temperament: String? = null,
    val weight: Eight? = null): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Eight::class.java.classLoader),
        parcel.readLong(),
        parcel.readParcelable(Image::class.java.classLoader),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Eight::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bredFor)
        parcel.writeString(breedGroup)
        parcel.writeParcelable(height, flags)
        parcel.writeLong(id)
        parcel.writeParcelable(image, flags)
        parcel.writeString(lifeSpan)
        parcel.writeString(name)
        parcel.writeString(origin)
        parcel.writeString(referenceImageID)
        parcel.writeString(temperament)
        parcel.writeParcelable(weight, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Breed> {
        override fun createFromParcel(parcel: Parcel): Breed {
            return Breed(parcel)
        }

        override fun newArray(size: Int): Array<Breed?> {
            return arrayOfNulls(size)
        }
    }
}

data class Eight (
    val imperial: String? = null,
    val metric: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imperial)
        parcel.writeString(metric)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Eight> {
        override fun createFromParcel(parcel: Parcel): Eight {
            return Eight(parcel)
        }

        override fun newArray(size: Int): Array<Eight?> {
            return arrayOfNulls(size)
        }
    }
}

@Entity(tableName="Image")
data class Image (
    @PrimaryKey @ColumnInfo(name = "id")
    val id: String,
    val height: Long,
    val url: String? = null,
    val width: Long
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readString(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeLong(height)
        parcel.writeString(url)
        parcel.writeLong(width)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }
}

const val EXTRA_BREED = "b675ef83cf570511c834bf4412a142c263187441"

