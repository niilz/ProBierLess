package de.niilz.probierless.tracking.repository

import de.niilz.probierless.ui.data.Drink

interface DrinkRepository {
    fun fetchAllDrinks(): List<Drink>
    fun addDrink(drink: Drink): Int
    fun removeDrink(id: Int)
    fun clearAllDrinks()
}