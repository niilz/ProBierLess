package de.niilz.probierless.tracking.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import de.niilz.probierless.ui.data.Drink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {

    init {
        Log.d(TAG, "SettingsViewModel initialized")
    }

    private val _settingsState = MutableStateFlow(/* TODO initDrinkState().toMutableMap() */null)
    val settingsState: StateFlow<MutableMap<Int, Drink>?> = _settingsState

    companion object {
        private val TAG: String = SettingsViewModel::class.java.simpleName
    }
}