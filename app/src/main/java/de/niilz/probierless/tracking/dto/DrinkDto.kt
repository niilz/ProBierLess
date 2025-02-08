package de.niilz.probierless.tracking.dto

data class DrinkDto(
    val name: String,
    val icon: String,
    val drinkSize: DrinkSize,
    val vol: Float
)