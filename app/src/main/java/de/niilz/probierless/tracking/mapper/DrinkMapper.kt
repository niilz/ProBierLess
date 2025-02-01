package de.niilz.probierless.tracking.mapper

import de.niilz.probierless.storage.entity.DrinkEntity
import de.niilz.probierless.tracking.dto.DrinkDto

fun toDto(drink: DrinkEntity): DrinkDto {
    return DrinkDto(drink.name, drink.icon)
}