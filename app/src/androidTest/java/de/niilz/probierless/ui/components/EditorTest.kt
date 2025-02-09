package de.niilz.probierless.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.niilz.probierless.ui.data.Drink
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditorTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun insertNonNumberForDrinkSizeThrows() {
        // given
        lateinit var drink: Drink
        rule.setContent { Editor({ d -> drink = d }) }

        val sizeInput = rule.onNodeWithTag(SIZE_INPUT_TAG)
        sizeInput.assertExists()
        sizeInput.performTextInput("NaN")

        // when
        // FIXME: Instead show a toast and check that toast is shown
        try {
            val createButton = rule.onNodeWithTag(ADD_BUTTON_TAG)
            createButton.performClick()
            fail("NaN drink sizes should throw")
        } catch (e: IllegalArgumentException) {
            // then
            assertNotNull(e)
            assertEquals("Foo", e.message)
        }
        fail("NaN drink sizes should throw IllegalArgumentException")
    }
}