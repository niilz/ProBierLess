package de.niilz.probierless.dev.preview

import de.niilz.probierless.tracking.dto.L
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.tracking.repository.DrinkRepositoryProvider
import de.niilz.probierless.ui.data.Drink

val emojiOptions = listOf("\uD83C\uDF7A", "\uD83C\uDF77", "\uD83C\uDF4E", "\uD83E\uDD43")

fun initDrinkRepositoryForPreview() {
    DrinkRepositoryProvider.init(object : DrinkRepository {
        var idSequence = 0;
        val previewDrinks = mutableMapOf<Int, Drink>()
        override fun fetchAllDrinks(): MutableMap<Int, Drink> {
            return previewDrinks
        }

        override fun addDrink(drink: Drink): Int {
            idSequence += 1
            previewDrinks.put(idSequence, drink)
            return idSequence
        }

        override fun removeDrink(id: Int) {
            previewDrinks.remove(id)
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