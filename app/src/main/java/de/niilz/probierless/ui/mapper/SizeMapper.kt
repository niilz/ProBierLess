package de.niilz.probierless.ui.mapper

import de.niilz.probierless.tracking.dto.Cl
import de.niilz.probierless.tracking.dto.DrinkSize
import de.niilz.probierless.tracking.dto.L
import de.niilz.probierless.tracking.dto.Ml

fun fromUi(size: String, type: String): Result<DrinkSize> {
    val sizeCleaned = size.trim().replace(",", ".")
    try {
        return when (type.lowercase()) {
            "ml" -> Result.success(Ml(sizeCleaned.toInt()))
            "cl" -> Result.success(Cl(sizeCleaned.toInt()))
            "l" -> Result.success(L(sizeCleaned.toFloat()))
            else -> Result.failure(IllegalArgumentException("Unsupported drink-size-type: $type"))
        }
    } catch (e: NumberFormatException) {
        return Result.failure(e)
    }
}