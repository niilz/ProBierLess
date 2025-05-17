package de.niilz.probierless.tracking.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import de.niilz.probierless.cross.ProbierLessException
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.tracking.repository.DrinkRepositoryProvider
import de.niilz.probierless.tracking.repository.RepositoryNotInitializedError
import de.niilz.probierless.ui.data.Drink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DrinkStateViewModel : ViewModel() {

    init {
        Log.d(TAG, "DrinkStateViewModel initialized")
    }

    private val _drinkState = MutableStateFlow(initDrinkState())
    val drinkState: StateFlow<MutableMap<Int, Drink>> = _drinkState

    fun addDrink(newDrink: Drink) {
        Log.d(TAG, "Add drink '$newDrink' to drink-repo")
        val drinkId = drinkRepo().addDrink(newDrink)
        Log.d(TAG, "Add drink '$newDrink' with id $drinkId to UI-state")
        drinkState.value.put(drinkId, newDrink)
    }

    fun deleteDrink(id: Int) {
        Log.d(TAG, "Removing drink with id '$id' from repo")
        drinkRepo().removeDrink(id)
        Log.d(TAG, "Removing drink with id '$id' from UI-state")
        drinkState.value.remove(id)
    }

    fun clearDrinks() {
        Log.d(TAG, "Clearing drinks from repo")
        drinkRepo().clearAllDrinks()
        Log.d(TAG, "Clearing drinks from UI-state")
        drinkState.value.clear()
    }

    fun countDrink(drinkId: Int) {
        val drink = drinkState.value[drinkId]
            ?: throw ProbierLessException("Could not increment drink with id $drinkId because it does not exist")
        _drinkState.value[drinkId] = drink.cloneAndIncrementCount()
        Log.d(TAG, "Incremented drink $drink")
    }

    private fun drinkRepo(): DrinkRepository = DrinkRepositoryProvider.getRepository()
        ?: throw RepositoryNotInitializedError("The DrinkRepository has not been initialized")

    private fun initDrinkState() =
        DrinkRepositoryProvider.getRepository()?.fetchAllDrinks() ?: emptyState()

    fun emptyState(): MutableMap<Int, Drink> {
        Log.w(TAG, "Could not fetch from Repository, returning empty state")
        return mutableStateMapOf()
    }


    companion object {
        private val TAG: String = DrinkStateViewModel::class.java.simpleName
    }
}