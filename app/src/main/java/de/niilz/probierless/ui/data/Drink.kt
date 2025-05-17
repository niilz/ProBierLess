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

    // We need the overrides because data classes generate equals and hashCode
    // only for the primary constructor. But we need count to be part of the equals.
    // So that the StateFlow is updated when the count changes.
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Drink

        if (vol != other.vol) return false
        if (count != other.count) return false
        if (name != other.name) return false
        if (icon != other.icon) return false
        if (drinkSize != other.drinkSize) return false

        return true
    }

    override fun hashCode(): Int {
        var result = vol.hashCode()
        result = 31 * result + count
        result = 31 * result + name.hashCode()
        result = 31 * result + icon.hashCode()
        result = 31 * result + drinkSize.hashCode()
        return result
    }

}