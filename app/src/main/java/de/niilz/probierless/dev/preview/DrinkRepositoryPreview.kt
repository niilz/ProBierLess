package de.niilz.probierless.dev.preview

import de.niilz.probierless.tracking.dto.DrinkDto
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.tracking.repository.DrinkRepositoryProvider

fun initDrinkRepositoryForPreview() {
    DrinkRepositoryProvider.init(object : DrinkRepository {
        override fun fetchAllDrinks(): List<DrinkDto> {
            return emptyList()
        }

        override fun addDrink(drink: DrinkDto) {
            TODO("Not yet implemented")
        }

        override fun clearAllDrinks() {
            TODO("Not yet implemented")
        }
    })
}