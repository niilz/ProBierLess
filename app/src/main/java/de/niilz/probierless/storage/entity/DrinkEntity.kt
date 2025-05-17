package de.niilz.probierless.storage.entity

import de.niilz.probierless.tracking.dto.DrinkSize

data class DrinkEntity(
    val name: String,
    val icon: String,
    val drinkSize: DrinkSize,
    val vol: Float,
    var count: Int = 0,
) {
    fun incrementCount() = count++
    fun resetCount() {
        count = 0
    }
}

