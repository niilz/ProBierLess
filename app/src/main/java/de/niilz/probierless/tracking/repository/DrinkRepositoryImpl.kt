package de.niilz.probierless.tracking.repository

import de.niilz.probierless.storage.StoreRoot
import de.niilz.probierless.storage.entity.DrinkEntity
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager

class DrinkRepositoryImpl(private val storeManager: EmbeddedStorageManager) : DrinkRepository {

    private val probierLessStore = storeManager.root() as StoreRoot

    override fun fetchAllDrinks(): MutableMap<Int, DrinkEntity> {
        return probierLessStore.drinks
    }

    override fun addDrink(drink: DrinkEntity): Int {
        probierLessStore.idSequence += 1
        storeManager.store(probierLessStore)
        probierLessStore.drinks.put(probierLessStore.idSequence, drink)
        storeManager.store(probierLessStore.drinks)
        return probierLessStore.idSequence
    }

    override fun updateDrink(
        id: Int,
        entity: DrinkEntity
    ) {
        probierLessStore.drinks.put(id, entity)
        storeManager.store(probierLessStore.drinks)
        // It's actually enough to store the entity
        // if it's already part of the object graph
        storeManager.store(entity)
    }

    override fun removeDrink(id: Int) {
        probierLessStore.drinks.remove(id)
        storeManager.store(probierLessStore.drinks)
    }

    override fun clearAllDrinks() {
        probierLessStore.drinks.clear()
        storeManager.store(probierLessStore.drinks)
    }
}