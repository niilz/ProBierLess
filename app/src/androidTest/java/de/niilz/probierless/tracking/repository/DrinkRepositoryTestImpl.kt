package de.niilz.probierless.tracking.repository

class DrinkRepositoryTestImpl : DrinkRepository {
    private val allDrinks = mutableListOf<DrinkDto>()

    override fun fetchAllDrinks(): List<DrinkDto> {
        return allDrinks
    }

    override fun addDrink(drink: DrinkDto) {
        allDrinks.add(drink)
    }

    override fun clearAllDrinks() {
        allDrinks.clear()
    }
}