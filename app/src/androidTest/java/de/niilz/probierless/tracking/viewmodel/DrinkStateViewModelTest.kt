package de.niilz.probierless.tracking.viewmodel

import de.niilz.probierless.storage.entity.DrinkEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

private const val TEST_DRINK = "test-drink"
private const val TEST_ICON = "test-icon"

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DrinkStateViewModelTest {

    private val drinkStateViewModel = DrinkStateViewModel()

    @Test
    fun drinkStateModelIsEmptyAtTheBeginning() {
        assertTrue(drinkStateViewModel.drinkState.isEmpty())
    }

    @Test
    fun drinkStateModelHoldsValueAfterInsert() {
        assertTrue(drinkStateViewModel.drinkState.isEmpty())

        // when
        drinkStateViewModel.addDrink(TEST_DRINK, TEST_ICON);

        // then
        assertEquals(1, drinkStateViewModel.drinkState.size)
        assertEquals(
            DrinkEntity(TEST_DRINK, TEST_ICON), drinkStateViewModel.drinkState.get(
                TEST_DRINK
            )
        )
    }
}
