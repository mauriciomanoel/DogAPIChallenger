package com.mauricio.dogapichallenger.breeds.models

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.mauricio.dogapichallenger.breeds.repository.Converters
import java.io.Serializable

typealias BreedsResult = ArrayList<Breed>
typealias BreedsByIdResult = ArrayList<BreedResultElement>

// Better this code to use Parcelable
data class BreedResultElement (
    val breeds: List<Breed>,
    val height: Long,
    val id: String,
    val url: String,
    val width: Long
) : Serializable

@Entity(tableName="Breed")
data class Breed (
    @SerializedName("bred_for")
    val bredFor: String? = null,
    @SerializedName("breed_group")
    val breedGroup: String? = null,
    val height: Eight,
    @PrimaryKey @ColumnInfo(name = "id")
    val id: Long,
    val image: Image,
    val lifeSpan: String? = null,
    val name: String,
    val origin: String? = null,
    @SerializedName("reference_image_id")
    val referenceImageID: String? = null,
    val temperament: String? = null,
    val weight: Eight): Serializable

data class Eight (
    val imperial: String? = null,
    val metric: String? = null
): Serializable

@Entity(tableName="Image")
data class Image (
    @PrimaryKey @ColumnInfo(name = "id")
    val id: String,
    val height: Long,
    val url: String? = null,
    val width: Long
): Serializable

const val EXTRA_BREED = "b675ef83cf570511c834bf4412a142c263187441"

