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

const val illegalDaysOfWeekNaNErrorTemplate = "Ungültige Eingabe für Tage pro Woche:"

class SettingsViewModel : ViewModel() {

    init {
        Log.d(TAG, "SettingsViewModel initialized")
    }

    private val _settingsState = MutableStateFlow(initDrinkState().toMutableMap())
    val settingsState: StateFlow<MutableMap<Int, Drink>> = _settingsState

    fun readAlcoholDayLimitGram(): Int {
        return settingsRepo().fetchAlcoholDayLimitGram()
    }

    fun addDrinkToDayLimit(drinkId: Int) {
        val drink = settingsRepo().fetchDrink(drinkId)
            ?: throw RepositoryNotInitializedError("Could not add alcohol from drink with id $drinkId because it does not exist")
        val currentDayLimit = settingsRepo().fetchAlcoholDayLimitGram()
        val alcoholAmount = Alcalculator.calcAlcoholAmount(drink)
        settingsRepo().storeAlcoholDayLimitGram(currentDayLimit + alcoholAmount)
    }

    fun resetAlcoholDayLimit() {
        settingsRepo().resetAlcoholDayLimitGram()
    }

    fun readMaxDaysPerWeek(): Int {
        return settingsRepo().fetchMaxDaysPerWeek()
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
            settingsRepo().storeMaxDaysPerWeek(maxDays)
        } catch (e: NumberFormatException) {
            Log.e(TAG, "Invalid input for max days per week: $maxDaysPerWeek", e)
            viewModelScope.launch {
                MessageSnackBarHub.addMessage("$illegalDaysOfWeekNaNErrorTemplate $maxDaysPerWeek")
            }
            return
        }
    }

    companion object {
        private val TAG: String = SettingsViewModel::class.java.simpleName
    }
}