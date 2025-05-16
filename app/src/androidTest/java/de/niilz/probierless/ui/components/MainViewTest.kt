package de.niilz.probierless.ui.components

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.InstrumentationRegistry
import de.niilz.probierless.tracking.dto.L
import de.niilz.probierless.tracking.repository.DrinkRepositoryProvider
import de.niilz.probierless.tracking.repository.DrinkRepositoryTestImpl
import de.niilz.probierless.ui.data.Drink
import de.niilz.probierless.ui.mapper.illegalDrinkSizeNaNErrorTemplate
import de.niilz.probierless.ui.navigation.UiState
import de.niilz.probierless.ui.navigation.UiStateEnum
import de.niilz.probierless.ui.theme.ProBierLessTheme
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainViewTest {

    private val testDrinkName = "test-drink-name"
    private val testIcon = "test-icon"
    private val testAmount = "500"
    private val testUnit = "ml"
    private val testVol = "4.8"

    @get:Rule
    val rule = createComposeRule()

    @Before
    fun setup() {
        DrinkRepositoryProvider.init(DrinkRepositoryTestImpl())
        UiState.state = UiStateEnum.EDITOR
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("de.niilz.probierless", appContext.packageName)
    }

    @Test
    fun canAddNewDrinkToUi() {
        rule.setContent { MainView {} }

        // Insert drink name
        val drinkInput = rule.onNodeWithTag(DRINK_INPUT_TAG)
        drinkInput.assertExists()
        drinkInput.performTextInput("Apfel")

        // Insert icon
        val appleEmoji = "\uD83C\uDF4E" // apple icon
        val iconInput = rule.onNodeWithTag(ICON_INPUT_TAG)
        iconInput.assertExists()
        iconInput.performTextInput(appleEmoji)

        // When
        rule.onNodeWithText(ADD_TEXT).performClick()

        // Then
        rule.onNodeWithText("Apfel").assertExists()
        rule.onNodeWithText(appleEmoji).assertExists()
    }

    @Test
    fun invalidDrinkAmountInputShowsUserErrors() {
        // given
        rule.setContent { ProBierLessTheme { MainView {} } }
        fillAllInputs()

        // when
        val amountInput = rule.onNodeWithTag(SIZE_INPUT_TAG)
        amountInput.performTextClearance()
        amountInput.performTextInput("NaN")
        amountInput.assertExists()
        rule.onNodeWithText(ADD_TEXT).performClick()

        // then
        val errorSnackBar = rule.onNodeWithText("Drink-Size", substring = true)
        errorSnackBar.assertExists()
        errorSnackBar.assertTextEquals("$illegalDrinkSizeNaNErrorTemplate NaN")
    }

    @Test
    fun deletingDrinkRemovesDrinkCounter() = runTest {
        // given
        val repo = DrinkRepositoryProvider.getRepository()!!
        repo.addDrink(Drink("test-drink", "test-icon", L(0.5f), 0.5f))
        assertEquals(1, repo.fetchAllDrinks().size)

        rule.setContent {
            MainView {}
        }

        val removeBeerButtonX = rule.onNodeWithText("❌")
        removeBeerButtonX.assertExists()

        // when
        removeBeerButtonX.performClick()

        // then
        assertTrue(repo.fetchAllDrinks().isEmpty())
    }

    private fun initDrinkRepository() {
        DrinkRepositoryProvider.init(DrinkRepositoryTestImpl())
    }

    private fun fillAllInputs() {
        // Insert drink name
        val drinkInput = rule.onNodeWithTag(DRINK_INPUT_TAG)
        drinkInput.assertExists()
        drinkInput.performTextInput(testDrinkName)
        val iconInput = rule.onNodeWithTag(ICON_INPUT_TAG)
        iconInput.assertExists()
        iconInput.performTextInput(testIcon)
        val amountInput = rule.onNodeWithTag(SIZE_INPUT_TAG)
        amountInput.assertExists()
        amountInput.performTextInput(testAmount)
        val unitInput = rule.onNodeWithTag(SIZE_TYPE_INPUT_TAG)
        unitInput.assertExists()
        unitInput.performTextInput(testUnit)
        val volInput = rule.onNodeWithTag(VOL_INPUT_TAG)
        volInput.assertExists()
        volInput.performTextInput(testVol)
    }

    companion object {
        private const val ADD_TEXT = "Hinzufügen"
    }
}
