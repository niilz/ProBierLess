package de.niilz.probierless.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.niilz.probierless.cross.ErrorSnackBarHub
import de.niilz.probierless.ui.mapper.illegalDrinkSizeNaNErrorTemplate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditorTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun insertNonNumberForDrinkSizeThrows() = runTest {
        // given
        rule.setContent { Editor({}, {}) }

        val sizeInput = rule.onNodeWithTag(SIZE_INPUT_TAG)
        sizeInput.assertExists()
        val sizeType = rule.onNodeWithTag(SIZE_TYPE_INPUT_TAG)
        sizeType.assertExists()

        // when
        sizeType.performTextInput("ml")
        sizeInput.performTextInput("NaN")
        val createButton = rule.onNodeWithTag(ADD_BUTTON_TAG)
        createButton.performClick()

        // then
        val error = ErrorSnackBarHub.errors.first()
        assertEquals("$illegalDrinkSizeNaNErrorTemplate NaN", error)
    }
}