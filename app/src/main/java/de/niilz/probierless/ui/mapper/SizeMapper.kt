package de.niilz.probierless.ui.mapper

import de.niilz.probierless.tracking.dto.Cl
import de.niilz.probierless.tracking.dto.DrinkSize
import de.niilz.probierless.tracking.dto.L
import de.niilz.probierless.tracking.dto.Ml

fun fromUi(size: String, type: String): DrinkSize {
    val sizeCleaned = size.trim().replace(",", ".")
    return when (type.lowercase()) {
        "ml" -> Ml(sizeCleaned.toInt())
        "cl" -> Cl(sizeCleaned.toInt())
        "l" -> L(sizeCleaned.toFloat())
        else -> throw IllegalArgumentException("Invalid drink-size-type: $size")
    }
}