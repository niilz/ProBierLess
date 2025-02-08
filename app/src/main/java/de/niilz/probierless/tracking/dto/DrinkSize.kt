package de.niilz.probierless.tracking.dto

data class Ml(private val ml: Int) : DrinkSize {
    override val sizeInMl: Int
        get() = ml
}

data class Cl(private val cl: Int) : DrinkSize {
    override val sizeInMl: Int
        get() = cl * 10
}

data class L(private val l: Float) : DrinkSize {
    override val sizeInMl: Int
        get() = (l * 10).toInt()
}

interface DrinkSize {
    val sizeInMl: Int
}
