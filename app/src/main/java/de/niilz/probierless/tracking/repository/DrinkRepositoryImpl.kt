package de.niilz.probierless.tracking.repository

import de.niilz.probierless.storage.StoreRoot
import de.niilz.probierless.tracking.dto.DrinkDto
import de.niilz.probierless.tracking.mapper.fromDto
import de.niilz.probierless.tracking.mapper.toDto

class DrinkRepositoryImpl(private val drinkStore: StoreRoot) : DrinkRepository {

    override fun fetchAllDrinks(): List<DrinkDto> {
        return drinkStore.drinks.map { toDto(it) }
    }

    override fun addDrink(drink: DrinkDto) {
        // FIXME: needs ref to store to call: store.store(drinks)
        drinkStore.drinks.add(fromDto(drink))
    }
}