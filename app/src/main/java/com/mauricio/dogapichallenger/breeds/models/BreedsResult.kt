package com.mauricio.dogapichallenger.breeds

import java.io.Serializable

typealias BreedsResult = ArrayList<Breed>

data class Breed (
    val bredFor: String,
    val breedGroup: String,
    val height: Eight,
    val id: Long,
    val image: Image,
    val lifeSpan: String,
    val name: String,
    val origin: String,
    val referenceImageID: String,
    val temperament: String,
    val weight: Eight,
    val countryCode: String? = null
): Serializable

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

