package de.niilz.probierless.tracking.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.tracking.repository.DrinkRepositoryProvider
import de.niilz.probierless.tracking.repository.RepositoryNotInitializedError
import de.niilz.probierless.ui.data.Drink

class DrinkStateViewModel : ViewModel() {

    init {
        Log.d(TAG, "DrinkStateViewModel initialized")
    }

    val drinkState = DrinkRepositoryProvider.getRepository()
        ?.fetchAllDrinks() ?: mutableStateMapOf()

    fun addDrink(newDrink: Drink) {
        Log.d(TAG, "Add drink '$newDrink' to drink-repo")
        val drinkId = drinkRepo().addDrink(newDrink)
        Log.d(TAG, "Add drink '$newDrink' to UI-state")
        drinkState.put(drinkId, newDrink)
    }

    fun deleteDrink(id: Int) {
        Log.d(TAG, "Removing drink with id '$id' from repo")
        drinkRepo().removeDrink(id)
        Log.d(TAG, "Removing drink with id '$id' from UI-state")
        drinkState?.remove(id)
            ?: throw ModifyDrinkStateException("Could not delete Drink from UI. $id did not exist or drink-state was uninitialized")
    }

    fun clearDrinks() {
        Log.d(TAG, "Clearing drinks from repo")
        drinkRepo().clearAllDrinks()
        Log.d(TAG, "Clearing drinks from UI-state")
        drinkState?.clear()
            ?: throw ModifyDrinkStateException("Could not clear Drink UI because drink-state was uninitialized")
    }

    fun countDrink(i: Int) {
        Log.e(TAG, "Counting is not implemented yet")
    }

    private fun drinkRepo(): DrinkRepository = DrinkRepositoryProvider.getRepository()
        ?: throw RepositoryNotInitializedError("The DrinkRepository has not been initialized")

    companion object {
        private val TAG: String = DrinkStateViewModel::class.java.simpleName
    }
}