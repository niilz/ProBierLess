package de.niilz.probierless.dev.preview

import de.niilz.probierless.tracking.dto.DrinkDto
import de.niilz.probierless.tracking.dto.L
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.tracking.repository.DrinkRepositoryProvider

val emojiOptions = listOf("\uD83C\uDF7A", "\uD83C\uDF77", "\uD83C\uDF4E", "\uD83E\uDD43")

fun initDrinkRepositoryForPreview() {
    DrinkRepositoryProvider.init(object : DrinkRepository {
        val previewDrinks = mutableListOf<DrinkDto>()
        override fun fetchAllDrinks(): List<DrinkDto> {
            return previewDrinks
        }

        override fun addDrink(drink: DrinkDto) {
            previewDrinks.add(drink)
        }

        override fun clearAllDrinks() {
            previewDrinks.clear()
        }
    })
}

fun addDrinks(amount: Int) {
    if (DrinkRepositoryProvider.getRepository() == null) {
        initDrinkRepositoryForPreview()
    }
    for (count in 1..amount) {
        val newDrink = DrinkDto("Drink-$count", emojiOptions.shuffled()[0], L(count * .1f), count.toFloat())
        DrinkRepositoryProvider
            .getRepository()?.addDrink(newDrink)
    }
}