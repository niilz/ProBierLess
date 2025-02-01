package de.niilz.probierless.tracking.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import de.niilz.probierless.storage.entity.DrinkEntity

class DrinkStateViewModel : ViewModel() {
    val drinkState = mutableStateMapOf<String, DrinkEntity>()

    fun init(drinkState: Map<String, DrinkEntity>) {
        this.drinkState.putAll(drinkState)

    }

    fun addDrink(newDrink: String, newDrinkIcon: String?) {
        drinkState[newDrink] = DrinkEntity(newDrink, newDrinkIcon ?: "$newDrink-icon")
    }

    fun clearDrinks() {
        drinkState.clear()
    }
}