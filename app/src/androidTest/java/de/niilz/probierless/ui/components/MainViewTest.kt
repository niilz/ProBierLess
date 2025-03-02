package de.niilz.probierless.ui.components

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.niilz.probierless.tracking.repository.DrinkRepositoryProvider
import de.niilz.probierless.tracking.repository.DrinkRepositoryTestImpl
import de.niilz.probierless.ui.mapper.illegalDrinkSizeNaNErrorTemplate
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
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
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("de.niilz.probierless", appContext.packageName)
    }

    @Test
    fun canAddNewDrinkToUi() {
        rule.setContent { MainView(true, {}) }

        // Insert drink name
        val drinkInput = rule.onNodeWithTag(DRINK_INPUT_TAG)
        drinkInput.assertExists()
        drinkInput.performTextInput("Apfel")

        // Insert icon
        val appleEmoji = "\uD83C\uDF4E"; // apple icon
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
        rule.setContent { MainView(true, {}) }
        fillAllInputs()

        // when
        val amountInput = rule.onNodeWithTag(SIZE_INPUT_TAG)
        amountInput.performTextClearance()
        amountInput.performTextInput("NaN")
        rule.onNodeWithText(ADD_TEXT).performClick()

        // then
        val errorSnackBar = rule.onNodeWithText("Drink-Size", substring = true)
        errorSnackBar.assertExists()
        errorSnackBar.assertTextEquals("$illegalDrinkSizeNaNErrorTemplate NaN")
    }

    private fun fillAllInputs() {
        // Insert drink name
        val drinkInput = rule.onNodeWithTag(DRINK_INPUT_TAG)
        drinkInput.performTextInput(testDrinkName)
        val iconInput = rule.onNodeWithTag(ICON_INPUT_TAG)
        iconInput.performTextInput(testIcon)
        val amountInput = rule.onNodeWithTag(SIZE_INPUT_TAG)
        amountInput.performTextInput(testAmount)
        val unitInput = rule.onNodeWithTag(SIZE_TYPE_INPUT_TAG)
        unitInput.performTextInput(testUnit)
        val volInput = rule.onNodeWithTag(VOL_INPUT_TAG)
        volInput.performTextInput(testVol)
    }

    companion object {
        private const val ADD_TEXT = "Hinzufügen"
    }
}
