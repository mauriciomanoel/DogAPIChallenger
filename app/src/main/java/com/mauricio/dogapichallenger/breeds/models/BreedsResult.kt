package com.mauricio.dogapichallenger.breeds.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

typealias BreedsResult = ArrayList<Breed>
typealias BreedsByIdResult = ArrayList<BreedResultElement>

data class BreedResultElement (
    val breeds: List<Breed>,
    val height: Long,
    val id: String,
    val url: String,
    val width: Long
) : Serializable

data class Breed (
    @SerializedName("bred_for")
    val bredFor: String,
    @SerializedName("breed_group")
    val breedGroup: String,
    val height: Eight,
    val id: Long,
    val image: Image? = null,
    val lifeSpan: String,
    val name: String,
    val origin: String,
    @SerializedName("reference_image_id")
    val referenceImageID: String? = null,
    val temperament: String,
    val weight: Eight): Serializable

data class Eight (
    val imperial: String,
    val metric: String
): Serializable

data class Image (
    val height: Long,
    val id: String,
    val url: String,
    val width: Long
): Serializable

const val EXTRA_BREED = "b675ef83cf570511c834bf4412a142c263187441"

