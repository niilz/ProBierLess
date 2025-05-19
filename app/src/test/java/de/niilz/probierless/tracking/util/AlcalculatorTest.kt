package de.niilz.probierless.tracking.util

import de.niilz.probierless.storage.entity.DrinkEntity
import de.niilz.probierless.tracking.dto.L
import de.niilz.probierless.tracking.dto.Ml
import junit.framework.TestCase.assertEquals
import org.junit.Test

class AlcalculatorTest {

    @Test
    fun `test that alcohol amount for 500ml beer with 5 vol-percent is 20g`() {
        val drink = DrinkEntity("Beer", "🍺", Ml(500), 5.0f)
        val expectedAlcoholAmount = (500 * (5.0f / 100) * 0.8).toInt() // 20g

        val actualAlcoholAmount = Alcalculator.calcAlcoholAmount(drink)

        assertEquals(
            "Expected $expectedAlcoholAmount but got $actualAlcoholAmount",
            expectedAlcoholAmount,
            actualAlcoholAmount
        )
    }

    @Test
    fun `test that alcohol amount for 7,5 l wine with 12,5 vol-percent is ~75g`() {
        val drink = DrinkEntity("Wine", "🍺", L(0.75f), 12.5f)
        val expectedAlcoholAmount = (750 * (12.5f / 100) * 0.8).toInt() // ~75g

        val actualAlcoholAmount = Alcalculator.calcAlcoholAmount(drink)

        assertEquals(
            "Expected $expectedAlcoholAmount but got $actualAlcoholAmount",
            expectedAlcoholAmount,
            actualAlcoholAmount
        )
    }
}
