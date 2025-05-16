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

    val drinkState = updateDrinkState()

    fun addDrink(newDrink: Drink) {
        Log.d(TAG, "Add drink '$newDrink' to drink-repo")
        val drinkId = drinkRepo().addDrink(newDrink)
        Log.d(TAG, "Add drink '$newDrink' with id $drinkId to UI-state")
        updateDrinkState()
    }

    fun deleteDrink(id: Int) {
        Log.d(TAG, "Removing drink with id '$id' from repo")
        drinkRepo().removeDrink(id)
        Log.d(TAG, "Removing drink with id '$id' from UI-state")
        updateDrinkState()
    }

    fun clearDrinks() {
        Log.d(TAG, "Clearing drinks from repo")
        drinkRepo().clearAllDrinks()
        Log.d(TAG, "Clearing drinks from UI-state")
        updateDrinkState()
    }

    fun countDrink(i: Int) {
        Log.e(TAG, "Counting is not implemented yet")
    }

    private fun drinkRepo(): DrinkRepository = DrinkRepositoryProvider.getRepository()
        ?: throw RepositoryNotInitializedError("The DrinkRepository has not been initialized")

    private fun updateDrinkState() =
        DrinkRepositoryProvider.getRepository()?.fetchAllDrinks() ?: emptyState()

    fun emptyState(): MutableMap<Int, Drink> {
        Log.w(TAG, "Could not fetch from Repository, returning empty state")
        return mutableStateMapOf()
    }


    companion object {
        private val TAG: String = DrinkStateViewModel::class.java.simpleName
    }
}