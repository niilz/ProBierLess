package de.niilz.probierless.ui.mapper

import de.niilz.probierless.tracking.dto.Cl
import de.niilz.probierless.tracking.dto.DrinkSize
import de.niilz.probierless.tracking.dto.L
import de.niilz.probierless.tracking.dto.Ml

const val illegalDrinkSizeNaNErrorTemplate = "Drink-Size must be a number, but was"
const val illegalDrinkSizeTypeErrorTemplate = "Unsupported drink-size-type"

fun fromUi(size: String, type: String): Result<DrinkSize> {
    val sizeCleaned = size.trim().replace(",", ".")
    return try {
        when (type.lowercase()) {
            "ml" -> Result.success(Ml(sizeCleaned.toInt()))
            "cl" -> Result.success(Cl(sizeCleaned.toInt()))
            "l" -> Result.success(L(sizeCleaned.toFloat()))
            else -> Result.failure(IllegalArgumentException("$illegalDrinkSizeTypeErrorTemplate $type"))
        }
    } catch (e: NumberFormatException) {
        Result.failure(IllegalArgumentException("$illegalDrinkSizeNaNErrorTemplate $size"))
    }
}