package de.niilz.probierless.tracking.repository

import de.niilz.probierless.storage.StoreRoot
import de.niilz.probierless.tracking.mapper.fromUi
import de.niilz.probierless.tracking.mapper.toUi
import de.niilz.probierless.ui.data.Drink
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager

class DrinkRepositoryImpl(private val storeManager: EmbeddedStorageManager) : DrinkRepository {

    private val drinkStore = storeManager.root() as StoreRoot

    override fun fetchAllDrinks(): List<Drink> {
        return drinkStore.drinks.map { toUi(it) }
    }

    // TODO: use Map instead of list and have an actual ID!
    override fun addDrink(drink: Drink): Int {
        val nextId = drinkStore.drinks.size
        drinkStore.drinks.add(fromUi(drink, nextId))
        storeManager.store(drinkStore.drinks)
        return nextId
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