package de.niilz.probierless.ui.data

import de.niilz.probierless.tracking.dto.DrinkSize

data class Drink(
    val name: String,
    val icon: String,
    val drinkSize: DrinkSize,
    val vol: Float,
) {
    private constructor(
        name: String,
        icon: String,
        drinkSize: DrinkSize,
        vol: Float,
        count: Int
    ) : this(name, icon, drinkSize, vol) {
        this.count = count
    }

    private var count = 0

    fun cloneAndIncrementCount() =
        Drink(this.name, this.icon, this.drinkSize, this.vol, this.count + 1)

    fun getCount() = count
}