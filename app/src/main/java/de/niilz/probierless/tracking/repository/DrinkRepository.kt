package de.niilz.probierless.tracking.repository

import de.niilz.probierless.storage.entity.DrinkEntity

interface DrinkRepository {
    fun fetchAllDrinks(): MutableMap<Int, DrinkEntity>
    fun addDrink(drink: DrinkEntity): Int
    fun updateDrink(id: Int, entity: DrinkEntity)
    fun removeDrink(id: Int)
    fun clearAllDrinks()
    fun fetchDrink(id: Int): DrinkEntity? {
        return fetchAllDrinks()[id]
    }
}