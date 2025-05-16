package de.niilz.probierless.tracking.repository

import de.niilz.probierless.ui.data.Drink

class DrinkRepositoryTestImpl : DrinkRepository {
    private var idSequence = 0
    private val allDrinks = mutableMapOf<Int, Drink>()

    override fun fetchAllDrinks(): MutableMap<Int, Drink> {
        return allDrinks
    }

    override fun addDrink(drink: Drink): Int {
        idSequence += 1
        allDrinks.put(idSequence, drink)
        return idSequence
    }

    override fun removeDrink(id: Int) {
        allDrinks.remove(id)
    }

    override fun clearAllDrinks() {
        allDrinks.clear()
    }
}