package de.niilz.probierless.tracking.repository

import de.niilz.probierless.ui.data.Drink

class DrinkRepositoryTestImpl : DrinkRepository {
    private val allDrinks = mutableListOf<Drink>()

    override fun fetchAllDrinks(): List<Drink> {
        return allDrinks
    }

    override fun addDrink(drink: Drink): Int {
        val id = allDrinks.size
        allDrinks.add(drink)
        return id
    }

    override fun removeDrink(id: Int) {
        allDrinks.removeAt(id)
    }

    override fun clearAllDrinks() {
        allDrinks.clear()
    }
}