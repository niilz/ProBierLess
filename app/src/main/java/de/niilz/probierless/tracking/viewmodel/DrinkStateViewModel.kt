package de.niilz.probierless.tracking.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import de.niilz.probierless.MainActivity
import de.niilz.probierless.tracking.dto.DrinkSize
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.ui.data.Drink
import de.niilz.probierless.ui.mapper.fromUi
import de.niilz.probierless.ui.mapper.toUi

class DrinkStateViewModel(private val drinkRepository: DrinkRepository) : ViewModel() {
    val drinkState = mutableStateListOf<Drink>()

    fun init(drinkState: List<Drink>) {
        this.drinkState.addAll(drinkState)
    }

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
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val drinkRepository =
                    (this[VIEW_MODEL_STORE_OWNER_KEY] as MainActivity).drinkRepository
                val drinkStateViewModel = DrinkStateViewModel(drinkRepository)
                val allDrinks = drinkRepository.fetchAllDrinks().map {
                    toUi(it)
                }
                drinkStateViewModel.init(allDrinks)
                drinkStateViewModel
            }
        }
        private val TAG: String = DrinkStateViewModel::class.java.simpleName
    }
}