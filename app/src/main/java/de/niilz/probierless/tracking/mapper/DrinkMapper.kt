package de.niilz.probierless.tracking.mapper

import de.niilz.probierless.storage.entity.DrinkEntity
import de.niilz.probierless.ui.data.Drink

fun toUi(drink: DrinkEntity) =
    Drink(drink.name, drink.icon, drink.drinkSize, drink.vol, drink.count)

fun fromUi(drink: Drink) =
    DrinkEntity(drink.name, drink.icon, drink.drinkSize, drink.vol, drink.count)