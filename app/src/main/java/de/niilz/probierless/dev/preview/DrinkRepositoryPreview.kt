package de.niilz.probierless.dev.preview

import de.niilz.probierless.storage.entity.DrinkEntity
import de.niilz.probierless.tracking.dto.L
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.tracking.repository.RepositoryProvider
import de.niilz.probierless.tracking.repository.SettingsRepository

val emojiOptions = listOf("\uD83C\uDF7A", "\uD83C\uDF77", "\uD83C\uDF4E", "\uD83E\uDD43")

fun initRepositoriesForPreview() {
    RepositoryProvider.init(DrinkRepositoryTestImpl(), SettingsRepositoryTestImpl())
}

class DrinkRepositoryTestImpl : DrinkRepository {
    var idSequence = 0;
    val previewDrinks = mutableMapOf<Int, DrinkEntity>()
    override fun fetchAllDrinks(): MutableMap<Int, DrinkEntity> {
        return previewDrinks
    }

    override fun addDrink(drink: DrinkEntity): Int {
        idSequence += 1
        previewDrinks.put(idSequence, drink)
        return idSequence
    }

    override fun updateDrink(
        id: Int,
        entity: DrinkEntity
    ) {
        previewDrinks.put(id, entity)
    }

    override fun removeDrink(id: Int) {
        previewDrinks.remove(id)
    }

    override fun clearAllDrinks() {
        previewDrinks.clear()
    }
}

class SettingsRepositoryTestImpl : SettingsRepository {
    private var alcoholDayLimit = 0
    private var alcoholDaysPerWeekLimit = 0

    override fun fetchAllDrinks(): MutableMap<Int, DrinkEntity> {
        return RepositoryProvider.getDrinkRepository()!!.fetchAllDrinks()
    }

    override fun fetchAlcoholDayLimitGram(): Int {
        return alcoholDayLimit
    }

    override fun storeAlcoholDayLimitGram(limitGram: Int) {
        alcoholDayLimit = limitGram
    }

    override fun fetchMaxDaysPerWeek(): Int {
        return alcoholDaysPerWeekLimit
    }

    override fun storeMaxDaysPerWeek(maxDaysPerWeek: Int) {
        alcoholDaysPerWeekLimit = maxDaysPerWeek
    }
}

fun addDrinks(amount: Int) {
    if (RepositoryProvider.getDrinkRepository() == null) {
        initRepositoriesForPreview()
    }
    for (count in 1..amount) {
        val newDrink =
            DrinkEntity("Drink-$count", emojiOptions.shuffled()[0], L(count * .1f), count.toFloat())
        RepositoryProvider
            .getDrinkRepository()?.addDrink(newDrink)
    }
}