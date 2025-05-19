package de.niilz.probierless.tracking.util

import de.niilz.probierless.storage.entity.DrinkEntity

private const val ALCOHOL_DENSITY = 0.8 // g/ml

object Alcalculator {
    fun calcAlcoholAmount(drink: DrinkEntity): Int {
        val alcoholVolume = drink.drinkSize.sizeInMl * (drink.vol / 100)
        return (alcoholVolume * ALCOHOL_DENSITY).toInt()
    }
}