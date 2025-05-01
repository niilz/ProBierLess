package de.niilz.probierless.tracking.repository

import androidx.compose.runtime.toMutableStateMap
import de.niilz.probierless.storage.StoreRoot
import de.niilz.probierless.tracking.mapper.fromUi
import de.niilz.probierless.tracking.mapper.toUi
import de.niilz.probierless.ui.data.Drink
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager

class DrinkRepositoryImpl(private val storeManager: EmbeddedStorageManager) : DrinkRepository {

    private val drinkStore = storeManager.root() as StoreRoot

    override fun fetchAllDrinks(): MutableMap<Int, Drink> {
        return drinkStore.drinks.map { Pair(it.key, toUi(it.value)) }.toMutableStateMap()
    }

    override fun addDrink(drink: Drink): Int {
        drinkStore.idSequence += 1
        storeManager.store(drinkStore)
        drinkStore.drinks.put(drinkStore.idSequence, fromUi(drink, drinkStore.idSequence))
        storeManager.store(drinkStore.drinks)
        return drinkStore.idSequence
    }

    override fun removeDrink(id: Int) {
        drinkStore.drinks.remove(id)
        storeManager.store(drinkStore.drinks)

    }

    override fun clearAllDrinks() {
        drinkStore.drinks.clear()
        storeManager.store(drinkStore.drinks)
    }
}