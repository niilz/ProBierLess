package de.niilz.probierless.dev.preview

import de.niilz.probierless.tracking.dto.L
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.tracking.repository.DrinkRepositoryProvider
import de.niilz.probierless.ui.data.Drink

val emojiOptions = listOf("\uD83C\uDF7A", "\uD83C\uDF77", "\uD83C\uDF4E", "\uD83E\uDD43")

fun initDrinkRepositoryForPreview() {
    DrinkRepositoryProvider.init(object : DrinkRepository {
        val previewDrinks = mutableListOf<Drink>()
        override fun fetchAllDrinks(): List<Drink> {
            return previewDrinks
        }

        // TODO: Use Map instead of a list and create actual IDs?
        override fun addDrink(drink: Drink): Int {
            previewDrinks.add(drink)
            return previewDrinks.size - 1
        }

        override fun removeDrink(id: Int) {
            previewDrinks.removeAt(id)
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
        val newDrink =
            Drink("Drink-$count", emojiOptions.shuffled()[0], L(count * .1f), count.toFloat())
        DrinkRepositoryProvider
            .getRepository()?.addDrink(newDrink)
    }
}