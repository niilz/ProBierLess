package de.niilz.probierless.tracking.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import de.niilz.probierless.MainActivity
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.ui.data.Drink
import de.niilz.probierless.ui.mapper.toUi

class DrinkStateViewModel(private val drinkRepository: DrinkRepository) : ViewModel() {
    val drinkState = mutableStateListOf<Drink>()

    fun init(drinkState: List<Drink>) {
        this.drinkState.addAll(drinkState)
    }

    fun addDrink(newDrink: String, newDrinkIcon: String?) {
        drinkState.add(Drink(newDrink, newDrinkIcon ?: "$newDrink-icon"))
    }

    fun clearDrinks() {
        drinkState.clear()
    }
}