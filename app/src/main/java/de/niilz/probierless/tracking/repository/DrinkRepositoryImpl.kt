package de.niilz.probierless.tracking.repository

import de.niilz.probierless.storage.StoreRoot
import de.niilz.probierless.storage.entity.DrinkEntity
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager

class DrinkRepositoryImpl(private val storeManager: EmbeddedStorageManager) : DrinkRepository {

    private val drinkStore = storeManager.root() as StoreRoot

    override fun fetchAllDrinks(): MutableMap<Int, DrinkEntity> {
        return drinkStore.drinks
    }

    override fun addDrink(drink: DrinkEntity): Int {
        drinkStore.idSequence += 1
        storeManager.store(drinkStore)
        drinkStore.drinks.put(drinkStore.idSequence, drink)
        storeManager.store(drinkStore.drinks)
        return drinkStore.idSequence
    }

    override fun updateDrink(
        id: Int,
        entity: DrinkEntity
    ) {
        drinkStore.drinks.put(id, entity)
        storeManager.store(drinkStore.drinks)
        // It's actually enough to store the entity
        // if it's already part of the object graph
        storeManager.store(entity)
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