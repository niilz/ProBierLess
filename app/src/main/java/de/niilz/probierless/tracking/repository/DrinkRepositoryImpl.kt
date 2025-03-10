package de.niilz.probierless.tracking.repository

import de.niilz.probierless.storage.StoreRoot
import de.niilz.probierless.tracking.dto.DrinkDto
import de.niilz.probierless.tracking.mapper.fromDto
import de.niilz.probierless.tracking.mapper.toDto
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager

class DrinkRepositoryImpl(private val storeManager: EmbeddedStorageManager) : DrinkRepository {

    private val drinkStore = storeManager.root() as StoreRoot

    override fun fetchAllDrinks(): List<DrinkDto> {
        return drinkStore.drinks.map { toDto(it) }
    }

    // TODO: use Map instead of list and have an actual ID!
    override fun addDrink(drink: DrinkDto): Int {
        drinkStore.drinks.add(fromDto(drink))
        storeManager.store(drinkStore.drinks)
        return drinkStore.drinks.size - 1
    }

    // TODO: use Map instead of list
    override fun removeDrink(id: Int) {
        drinkStore.drinks.removeAt(id)
        storeManager.store(drinkStore.drinks)

    }

    override fun clearAllDrinks() {
        drinkStore.drinks.clear()
        storeManager.store(drinkStore.drinks)
    }
}