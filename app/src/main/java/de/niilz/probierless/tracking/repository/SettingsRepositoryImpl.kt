package de.niilz.probierless.tracking.repository

import de.niilz.probierless.storage.StoreRoot
import de.niilz.probierless.storage.entity.DrinkEntity
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager

class SettingsRepositoryImpl(private val storeManager: EmbeddedStorageManager) :
    SettingsRepository {

    private val probierLessStore = storeManager.root() as StoreRoot

    override fun fetchAllDrinks(): MutableMap<Int, DrinkEntity> {
        return probierLessStore.drinks
    }

    override fun fetchAlcoholDayLimitGram(): Int {
        return probierLessStore.alcoholDayLimit
    }

    override fun storeAlcoholDayLimitGram(newLimit: Int) {
        probierLessStore.alcoholDayLimit = newLimit
        storeManager.store(probierLessStore)
    }
}