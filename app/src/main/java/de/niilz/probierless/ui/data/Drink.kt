package de.niilz.probierless.ui.data

import de.niilz.probierless.tracking.dto.DrinkSize

data class Drink(
    val name: String,
    val icon: String,
    val drinkSize: DrinkSize,
    val vol: Float,
)