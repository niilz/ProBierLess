package de.niilz.probierless.tracking.viewmodel

import de.niilz.probierless.tracking.dto.DrinkDto
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.ui.data.Drink
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
    private val repoMock = object : DrinkRepository {
        private val store = mutableListOf<DrinkDto>()
        override fun fetchAllDrinks() = store
        override fun addDrink(drink: DrinkDto) {
            store.add(drink)
        }
    }

    private val drinkStateViewModel = DrinkStateViewModel(repoMock)

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
            Drink(TEST_DRINK, TEST_ICON),
            drinkStateViewModel.drinkState.get(0)
        )
        val allRepoDrinks = repoMock.fetchAllDrinks();
        assertEquals(1, allRepoDrinks.size)
        assertEquals(
            DrinkDto(TEST_DRINK, TEST_ICON),
            allRepoDrinks.get(0)
        )
    }
}
