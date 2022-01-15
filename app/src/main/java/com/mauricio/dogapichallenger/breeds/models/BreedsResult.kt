package com.mauricio.dogapichallenger.breeds

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
)

data class Eight (
    val imperial: String,
    val metric: String
)

data class Image (
    val height: Long,
    val id: String,
    val url: String,
    val width: Long
)

