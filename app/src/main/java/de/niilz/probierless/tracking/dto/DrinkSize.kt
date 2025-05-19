package de.niilz.probierless.tracking.dto

data class Ml(private val ml: Int) : DrinkSize {
    override fun toString() = "${ml}ml"
    override val sizeInMl: Int
        get() = ml
}

data class Cl(private val cl: Int) : DrinkSize {
    override fun toString() = "${cl}cl"
    override val sizeInMl: Int
        get() = cl * 10
}

data class L(private val l: Float) : DrinkSize {
    override fun toString() = "${l}l"
    override val sizeInMl: Int
        get() = (l * 1000).toInt()
}

interface DrinkSize {
    val sizeInMl: Int
}
