package de.niilz.probierless.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
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

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("de.niilz.probierless", appContext.packageName)
    }

    @Test
    fun canAddNewDrinkToUi() {
        rule.setContent { MainView() }

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
        rule.onNodeWithText("createDrink").performClick()

        // Then
        rule.onNodeWithText("Hello Apfel $appleEmoji!").assertExists()
    }
}
