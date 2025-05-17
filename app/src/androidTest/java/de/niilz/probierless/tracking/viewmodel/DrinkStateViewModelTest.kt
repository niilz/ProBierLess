package de.niilz.probierless.tracking.viewmodel

import de.niilz.probierless.storage.entity.DrinkEntity
import de.niilz.probierless.tracking.dto.Ml
import de.niilz.probierless.tracking.repository.DrinkRepositoryProvider
import de.niilz.probierless.tracking.repository.DrinkRepositoryTestImpl
import de.niilz.probierless.ui.data.Drink
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

private const val TEST_DRINK = "test-drink"
private const val TEST_ICON = "test-icon"
private val TEST_SIZE = Ml(330)
private const val TEST_VOL = 4.9f

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DrinkStateViewModelTest {

    private lateinit var drinkStateViewModel: DrinkStateViewModel

    @Before
    fun setup() {
        DrinkRepositoryProvider.init(DrinkRepositoryTestImpl())
        drinkStateViewModel = DrinkStateViewModel()
    }


    @Test
    fun drinkStateModelIsEmptyAtTheBeginning() {
        assertTrue(drinkStateViewModel.drinkState.value.isEmpty() == true)
    }

    @Test
    fun drinkStateModelHoldsValueAfterInsert() {
        assertTrue(drinkStateViewModel.drinkState.value.isEmpty() == true)

        // when
        val newDrink = Drink(TEST_DRINK, TEST_ICON, TEST_SIZE, TEST_VOL)
        drinkStateViewModel.addDrink(newDrink)

        // then
        assertEquals(1, drinkStateViewModel.drinkState.value.size)
        assertEquals(
            Drink(TEST_DRINK, TEST_ICON, TEST_SIZE, TEST_VOL),
            drinkStateViewModel.drinkState.value[1]
        )
        val allRepoDrinks = DrinkRepositoryProvider.getRepository()?.fetchAllDrinks()
        assertEquals(1, allRepoDrinks?.size)
        assertEquals(
            DrinkEntity(TEST_DRINK, TEST_ICON, TEST_SIZE, TEST_VOL),
            allRepoDrinks?.get(1)
        )
    }

    @Test
    fun clearingRemovesUiAndRepoState() {
        assertTrue(drinkStateViewModel.drinkState.value.isEmpty() == true)
        // given
        val newDrink = Drink(TEST_DRINK, TEST_ICON, TEST_SIZE, TEST_VOL)
        drinkStateViewModel.addDrink(newDrink)
        assertEquals(1, drinkStateViewModel.drinkState.value.size)

        // when
        drinkStateViewModel.clearDrinks()

        // then
        assertTrue(
            "viewmodel-state should be empty",
            drinkStateViewModel.drinkState.value.isEmpty() == true
        )
        assertTrue(
            "repo should be empty",
            DrinkRepositoryProvider.getRepository()?.fetchAllDrinks()?.isEmpty() == true
        )
    }
}
