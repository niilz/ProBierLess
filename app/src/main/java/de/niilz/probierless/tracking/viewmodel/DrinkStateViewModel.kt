package de.niilz.probierless.tracking.viewmodel

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import de.niilz.probierless.tracking.repository.DrinkRepositoryProvider
import de.niilz.probierless.ui.data.Drink
import de.niilz.probierless.ui.mapper.fromUi
import de.niilz.probierless.ui.mapper.toUi

class DrinkStateViewModel : ViewModel() {

    private val drinkRepository = DrinkRepositoryProvider.getRepository()

    val drinkState = drinkRepository.fetchAllDrinks().map { toUi(it) }.toMutableStateList()

    fun addDrink(newDrink: Drink) {
        Log.d(TAG, "Add drink '$newDrink' to UI-state")
        drinkState.add(newDrink)
        Log.d(TAG, "Add drink '$newDrink' to drink-repo")
        drinkRepository.addDrink(fromUi(newDrink))
    }

    fun clearDrinks() {
        drinkState.clear()
        drinkRepository.clearAllDrinks()
    }

    companion object {
        private val TAG: String = DrinkStateViewModel::class.java.simpleName
    }
}