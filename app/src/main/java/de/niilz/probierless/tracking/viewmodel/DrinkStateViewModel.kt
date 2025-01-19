package de.niilz.probierless.tracking.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import de.niilz.probierless.tracking.dto.Drink

class DrinkStateViewModel : ViewModel() {
    val drinkState = mutableStateMapOf<String, Drink>()

    fun init(drinkState: Map<String, Drink>) {
        this.drinkState.putAll(drinkState)

    }

    fun addDrink(newDrink: String, newDrinkIcon: String?) {
        drinkState[newDrink] = Drink(newDrink, newDrinkIcon ?: "$newDrink-icon")
    }

    fun clearDrinks() {
        drinkState.clear()
    }
}