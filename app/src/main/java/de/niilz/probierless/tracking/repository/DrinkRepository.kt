package de.niilz.probierless.tracking.repository

import de.niilz.probierless.tracking.dto.DrinkDto

interface DrinkRepository {
    fun fetchAllDrinks(): List<DrinkDto>
    fun addDrink(drink: DrinkDto)
}