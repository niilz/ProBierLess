package de.niilz.probierless.tracking.repository

import de.niilz.probierless.storage.entity.DrinkEntity

interface SettingsRepository {
    fun fetchAllDrinks(): MutableMap<Int, DrinkEntity>
    fun fetchAlcoholDayLimit(): Int
    fun storeAlcoholDayLimit(limit: Int)
    fun resetAlcoholDayLimit() {
        storeAlcoholDayLimit(0)
    }
}