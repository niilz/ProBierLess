package de.niilz.probierless.tracking.viewmodel

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.tracking.repository.DrinkRepositoryProvider
import de.niilz.probierless.tracking.repository.RepositoryNotInitializedError
import de.niilz.probierless.ui.data.Drink
import de.niilz.probierless.ui.mapper.fromUi
import de.niilz.probierless.ui.mapper.toUi

class DrinkStateViewModel : ViewModel() {

    val drinkState = DrinkRepositoryProvider.getRepository()
        ?.fetchAllDrinks()
        ?.map { toUi(it) }
        ?.toMutableStateList()

    fun addDrink(newDrink: Drink) {
        Log.d(TAG, "Add drink '$newDrink' to drink-repo")
        drinkRepo().addDrink(fromUi(newDrink))
        Log.d(TAG, "Add drink '$newDrink' to UI-state")
        drinkState?.add(newDrink)
    }

    fun clearDrinks() {
        drinkRepo().clearAllDrinks()
    }

    private fun drinkRepo(): DrinkRepository = DrinkRepositoryProvider.getRepository()
        ?: throw RepositoryNotInitializedError("The DrinkRepository has not been initialized")

    companion object {
        private val TAG: String = DrinkStateViewModel::class.java.simpleName
    }
}