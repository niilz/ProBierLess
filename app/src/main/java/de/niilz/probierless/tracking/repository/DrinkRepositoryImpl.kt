package de.niilz.probierless.tracking.repository

import de.niilz.probierless.storage.StoreRoot
import de.niilz.probierless.tracking.mapper.fromUi
import de.niilz.probierless.tracking.mapper.toUi
import de.niilz.probierless.ui.data.Drink
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager

class DrinkRepositoryImpl(private val storeManager: EmbeddedStorageManager) : DrinkRepository {

    private var idSequence = 0
    private val drinkStore = storeManager.root() as StoreRoot

    override fun fetchAllDrinks(): List<Drink> {
        return drinkStore.drinks.map { toUi(it.value) }
    }

    override fun addDrink(drink: Drink): Int {
        idSequence += 1
        drinkStore.drinks.put(idSequence, fromUi(drink, idSequence))
        storeManager.store(drinkStore.drinks)
        return idSequence
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