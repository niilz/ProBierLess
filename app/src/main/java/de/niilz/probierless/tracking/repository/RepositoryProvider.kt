package de.niilz.probierless.tracking.repository

import android.util.Log

object RepositoryProvider {
    private var drinkRepository: DrinkRepository? = null
    private var settingsRepository: SettingsRepository? = null

    fun init(drinkRepository: DrinkRepository, settingsRepository: SettingsRepository) {
        Log.d(TAG, "Initializing DrinkRepository with ${drinkRepository.javaClass.simpleName}")
        RepositoryProvider.drinkRepository = drinkRepository
        Log.d(
            TAG,
            "Initializing SettingsRepository with ${settingsRepository.javaClass.simpleName}"
        )
        RepositoryProvider.settingsRepository = settingsRepository
    }

    fun getDrinkRepository(): DrinkRepository? {
        return drinkRepository
    }

    fun getSettingsRepository(): SettingsRepository? {
        return settingsRepository
    }

    private val TAG = RepositoryProvider::class.java.simpleName
}