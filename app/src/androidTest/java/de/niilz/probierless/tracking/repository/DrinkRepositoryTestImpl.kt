package de.niilz.probierless.tracking.repository

import de.niilz.probierless.storage.entity.DrinkEntity

class DrinkRepositoryTestImpl : DrinkRepository {
    private var idSequence = 0
    private val allDrinks = mutableMapOf<Int, DrinkEntity>()

    override fun fetchAllDrinks(): MutableMap<Int, DrinkEntity> {
        return allDrinks
    }

    override fun addDrink(drink: DrinkEntity): Int {
        idSequence += 1
        allDrinks.put(idSequence, drink)
        return idSequence
    }

    override fun updateDrink(
        id: Int,
        entity: DrinkEntity
    ) {
        allDrinks.put(id, entity)
    }

    override fun removeDrink(id: Int) {
        allDrinks.remove(id)
    }

    override fun clearAllDrinks() {
        allDrinks.clear()
    }
}