package de.niilz.probierless.ui.components

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import de.niilz.probierless.dev.preview.DrinkRepositoryTestImpl
import de.niilz.probierless.dev.preview.SettingsRepositoryTestImpl
import de.niilz.probierless.tracking.repository.RepositoryProvider
import de.niilz.probierless.ui.mapper.illegalDrinkSizeNaNErrorTemplate
import de.niilz.probierless.ui.navigation.UiState
import de.niilz.probierless.ui.navigation.UiStateEnum
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

private val testDrinkName = "test-drink-name"
private val testIcon = "test-icon"
private val testAmount = "500"
private val testUnit = "ml"
private val testVol = "4.8"

class AppViewTest {

    @get:Rule
    val rule = createComposeRule()

    @Before
    fun setup() {
        initRepositories()
        UiState.state = UiStateEnum.EDITOR
    }

    @Test
    fun invalidDrinkAmountInputShowsUserErrors() {
        // given
        rule.setContent { AppView() }
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
   
    private fun initRepositories() {
        RepositoryProvider.init(DrinkRepositoryTestImpl(), SettingsRepositoryTestImpl())
    }

    companion object {
        private const val ADD_TEXT = "Hinzufügen"
    }
}
