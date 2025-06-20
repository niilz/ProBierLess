package de.niilz.probierless.tracking.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.niilz.probierless.cross.MessageSnackBarHub
import de.niilz.probierless.storage.entity.DrinkEntity
import de.niilz.probierless.tracking.mapper.toUi
import de.niilz.probierless.tracking.repository.RepositoryNotInitializedError
import de.niilz.probierless.tracking.repository.RepositoryProvider
import de.niilz.probierless.tracking.repository.SettingsRepository
import de.niilz.probierless.tracking.util.Alcalculator
import de.niilz.probierless.ui.data.Drink
import de.niilz.probierless.ui.data.resetCount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

const val unsupportedDaysOfWeekErrorTemplate =
    "Tage pro Woche muss eine Zahl zwischen 1 und 7 sein."

class SettingsViewModel : ViewModel() {

    init {
        Log.d(TAG, "SettingsViewModel initialized")
    }

    private val _drinksSettingsState = MutableStateFlow(initDrinkState().toMutableMap())
    val drinksSettingsState: StateFlow<MutableMap<Int, Drink>> = _drinksSettingsState
    private val _alcoholDayLimitGram = MutableStateFlow(settingsRepo().fetchAlcoholDayLimitGram())
    val alcoholDayLimitGram: StateFlow<Int> = _alcoholDayLimitGram
    private val _daysPerWeekState = MutableStateFlow(settingsRepo().fetchMaxDaysPerWeek())
    val daysPerWeekState: StateFlow<Int> = _daysPerWeekState

    fun addDrinkToDayLimit(drinkId: Int) {
        val drink = settingsRepo().fetchDrink(drinkId)
            ?: throw RepositoryNotInitializedError("Could not add alcohol from drink with id $drinkId because it does not exist")
        val currentDayLimit = settingsRepo().fetchAlcoholDayLimitGram()
        val alcoholAmount = Alcalculator.calcAlcoholAmount(drink)
        settingsRepo().storeAlcoholDayLimitGram(currentDayLimit + alcoholAmount)
        _alcoholDayLimitGram.value = settingsRepo().fetchAlcoholDayLimitGram()
    }

    fun resetAlcoholDayLimit() {
        settingsRepo().resetAlcoholDayLimitGram()
        _alcoholDayLimitGram.value = settingsRepo().fetchAlcoholDayLimitGram()
    }

    private fun initDrinkState() =
        RepositoryProvider.getDrinkRepository()?.fetchAllDrinks()
            ?.map { Pair(it.key, mapToUiAndResetCount(it.value)) }?.toMap() ?: emptyState()

    private fun mapToUiAndResetCount(drinkEntity: DrinkEntity): Drink {
        val drink = toUi(drinkEntity)
        drink.resetCount()
        return drink
    }

    private fun settingsRepo(): SettingsRepository = RepositoryProvider.getSettingsRepository()
        ?: throw RepositoryNotInitializedError("The SettingsRepository has not been initialized")

    fun emptyState(): MutableMap<Int, Drink> {
        Log.w(TAG, "Could not fetch from Repository, returning empty state")
        return mutableStateMapOf()
    }

    fun writeMaxDaysPerWeek(maxDaysPerWeek: String) {
        try {
            val maxDays = maxDaysPerWeek.toInt()
            if (maxDays < 1 || maxDays > 7) {
                Log.e(TAG, "Max days per week must be between 1 and 7: $maxDays")
                viewModelScope.launch {
                    MessageSnackBarHub.addMessage(unsupportedDaysOfWeekErrorTemplate)
                }
                return
            }
            settingsRepo().storeMaxDaysPerWeek(maxDays)
            _daysPerWeekState.value = settingsRepo().fetchMaxDaysPerWeek()
        } catch (e: NumberFormatException) {
            Log.e(TAG, "Invalid input for max days per week: $maxDaysPerWeek", e)
            viewModelScope.launch {
                MessageSnackBarHub.addMessage(unsupportedDaysOfWeekErrorTemplate)
            }
            return
        }
    }

    companion object {
        private val TAG: String = SettingsViewModel::class.java.simpleName
    }
}