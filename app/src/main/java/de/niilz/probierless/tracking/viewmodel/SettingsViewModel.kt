package de.niilz.probierless.tracking.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import de.niilz.probierless.tracking.mapper.toUiCountZero
import de.niilz.probierless.tracking.repository.RepositoryNotInitializedError
import de.niilz.probierless.tracking.repository.RepositoryProvider
import de.niilz.probierless.tracking.repository.SettingsRepository
import de.niilz.probierless.ui.data.Drink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {

    init {
        Log.d(TAG, "SettingsViewModel initialized")
    }

    private val _settingsState = MutableStateFlow(initDrinkState().toMutableMap())
    val settingsState: StateFlow<MutableMap<Int, Drink>> = _settingsState

    private fun initDrinkState() =
        DrinkRepositoryProvider.getRepository()?.fetchAllDrinks()
            ?.map { Pair(it.key, toUiCountZero(it.value)) }?.toMap() ?: emptyState()

    private fun drinkRepo(): DrinkRepository = DrinkRepositoryProvider.getRepository()
        ?: throw RepositoryNotInitializedError("The DrinkRepository has not been initialized")

    fun emptyState(): MutableMap<Int, Drink> {
        Log.w(TAG, "Could not fetch from Repository, returning empty state")
        return mutableStateMapOf()
    }

    companion object {
        private val TAG: String = SettingsViewModel::class.java.simpleName
    }
}