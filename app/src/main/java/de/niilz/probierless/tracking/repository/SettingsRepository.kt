package de.niilz.probierless.tracking.repository

import de.niilz.probierless.storage.entity.DrinkEntity

interface SettingsRepository {
    fun fetchAllDrinks(): MutableMap<Int, DrinkEntity>
    fun fetchAlcoholDayLimitGram(): Int
    fun storeAlcoholDayLimitGram(limitGram: Int)

    fun fetchDrink(id: Int): DrinkEntity? {
        return fetchAllDrinks()[id]
    }

    fun resetAlcoholDayLimitGram() {
        storeAlcoholDayLimitGram(0)
    }

    fun fetchMaxDaysPerWeek(): Int
    fun storeMaxDaysPerWeek(maxDaysPerWeek: Int)
}