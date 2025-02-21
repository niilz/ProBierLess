package de.niilz.probierless.tracking.repository

import android.util.Log

object DrinkRepositoryProvider {
    private lateinit var drinkRepository: DrinkRepository

    fun init(drinkRepository: DrinkRepository) {
        Log.d(TAG, "Initializing DrinkRepository with ${drinkRepository.javaClass.simpleName}")
        DrinkRepositoryProvider.drinkRepository = drinkRepository
    }

    fun getRepository(): DrinkRepository {
        return drinkRepository
    }

    private val TAG = DrinkRepository::class.java.simpleName
}