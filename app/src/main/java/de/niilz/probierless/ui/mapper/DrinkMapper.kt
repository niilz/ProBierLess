package de.niilz.probierless.ui.mapper

import de.niilz.probierless.tracking.dto.DrinkDto
import de.niilz.probierless.ui.data.Drink

fun toUi(drink: DrinkDto): Drink {
    return Drink(drink.name, drink.icon)
}