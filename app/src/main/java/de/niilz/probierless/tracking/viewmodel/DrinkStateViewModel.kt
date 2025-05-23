package de.niilz.probierless.tracking.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import de.niilz.probierless.cross.ProbierLessException
import de.niilz.probierless.tracking.mapper.fromUi
import de.niilz.probierless.tracking.mapper.toUi
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.tracking.repository.RepositoryNotInitializedError
import de.niilz.probierless.tracking.repository.RepositoryProvider
import de.niilz.probierless.ui.data.Drink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DrinkStateViewModel : ViewModel() {

    init {
        Log.d(TAG, "DrinkStateViewModel initialized")
    }

    private val _drinkState = MutableStateFlow(initDrinkState().toMutableMap())
    val drinkState: StateFlow<MutableMap<Int, Drink>> = _drinkState

    fun addDrink(newDrink: Drink) {
        Log.d(TAG, "Add drink '$newDrink' to drink-repo")
        val drinkEntity = fromUi(newDrink)
        val drinkId = drinkRepo().addDrink(drinkEntity)
        Log.d(TAG, "Add drink '$newDrink' with id $drinkId to UI-state")
        _drinkState.value = _drinkState.value.toMutableMap().apply {
            this.put(drinkId, newDrink)
        }
    }

    fun deleteDrink(id: Int) {
        Log.d(TAG, "Removing drink with id '$id' from repo")
        drinkRepo().removeDrink(id)
        Log.d(TAG, "Removing drink with id '$id' from UI-state")
        _drinkState.value = _drinkState.value.toMutableMap().apply { this.remove(id) }
    }

    fun clearDrinks() {
        Log.d(TAG, "Clearing drinks from repo")
        drinkRepo().clearAllDrinks()
        Log.d(TAG, "Clearing drinks from UI-state")
        _drinkState.value = mutableStateMapOf()
    }

    fun countDrink(drinkId: Int) {
        val drinkEntity = drinkRepo().fetchDrink(drinkId)
            ?: throw ProbierLessException("Could not increment drink with id $drinkId because it does not exist")
        drinkEntity.incrementCount()
        drinkRepo().updateDrink(drinkId, drinkEntity)
        _drinkState.value = _drinkState.value.toMutableMap().apply {
            this[drinkId] = toUi(drinkEntity)
        }
        Log.d(TAG, "Incremented drink $drinkEntity")
    }

    fun resetCounts() {
        Log.d(TAG, "Resetting all drink counts")
        drinkRepo().fetchAllDrinks().forEach {
            it.value.resetCount()
            drinkRepo().updateDrink(it.key, it.value)
        }
        _drinkState.value = initDrinkState().toMutableMap()
    }

    private fun drinkRepo(): DrinkRepository = RepositoryProvider.getDrinkRepository()
        ?: throw RepositoryNotInitializedError("The DrinkRepository has not been initialized")

    private fun initDrinkState() =
        RepositoryProvider.getDrinkRepository()?.fetchAllDrinks()
            ?.map { Pair(it.key, toUi(it.value)) }?.toMap() ?: emptyState()

    fun emptyState(): MutableMap<Int, Drink> {
        Log.w(TAG, "Could not fetch from Repository, returning empty state")
        return mutableStateMapOf()
    }


    companion object {
        private val TAG: String = DrinkStateViewModel::class.java.simpleName
    }
}