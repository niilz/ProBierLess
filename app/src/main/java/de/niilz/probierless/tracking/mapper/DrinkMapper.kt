package de.niilz.probierless.tracking.mapper

import de.niilz.probierless.storage.entity.DrinkEntity
import de.niilz.probierless.tracking.dto.DrinkDto

fun toDto(drink: DrinkEntity) = DrinkDto(drink.name, drink.icon, drink.drinkSize, drink.vol)
fun fromDto(drink: DrinkDto) = DrinkEntity(drink.name, drink.icon, drink.drinkSize, drink.vol)