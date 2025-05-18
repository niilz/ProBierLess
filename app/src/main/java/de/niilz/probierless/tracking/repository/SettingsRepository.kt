package de.niilz.probierless.tracking.repository

import de.niilz.probierless.storage.entity.DrinkEntity

interface SettingsRepository {
    fun fetchAllDrinks(): MutableMap<Int, DrinkEntity>
    fun fetchAlcoholDayLimit(): Int
    fun storeAlcoholDayLimit(limit: Int)

    fun fetchDrink(id: Int): DrinkEntity? {
        return fetchAllDrinks()[id]
    }

    fun resetAlcoholDayLimit() {
        storeAlcoholDayLimit(0)
    }
}