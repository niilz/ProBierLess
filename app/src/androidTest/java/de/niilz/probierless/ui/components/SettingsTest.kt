package de.niilz.probierless.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.niilz.probierless.cross.MessageSnackBarHub
import de.niilz.probierless.dev.preview.DrinkRepositoryTestImpl
import de.niilz.probierless.dev.preview.SettingsRepositoryTestImpl
import de.niilz.probierless.tracking.repository.RepositoryProvider
import de.niilz.probierless.tracking.viewmodel.unsupportedDaysOfWeekErrorTemplate
import de.niilz.probierless.ui.components.settings.DAYS_PER_WEEK_INPUT_TAG
import de.niilz.probierless.ui.components.settings.SAVE_BUTTON_TAG
import de.niilz.probierless.ui.components.settings.Settings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsTest {

    @get:Rule
    val rule = createComposeRule()

    @Before
    fun setup() {
        initRepositories()
    }

    @Test
    fun insertNonNumberForWeek() = runTest {
        // given
        rule.setContent {
            Settings()
        }

        val weekInput = rule.onNodeWithTag(DAYS_PER_WEEK_INPUT_TAG)
        weekInput.assertExists()

        // when
        weekInput.performTextInput("NaN")
        val createButton = rule.onNodeWithTag(SAVE_BUTTON_TAG)
        createButton.performClick()

        // then
        val error = MessageSnackBarHub.messages.first()
        assertEquals("$unsupportedDaysOfWeekErrorTemplate NaN", error)
    }

    @Test
    fun daysPerWeekMustBeBetween1And7() = runTest {
        // given
        rule.setContent {
            Settings()
        }

        val weekInput = rule.onNodeWithTag(DAYS_PER_WEEK_INPUT_TAG)
        weekInput.assertExists()

        // when
        weekInput.performTextInput("42")
        val createButton = rule.onNodeWithTag(SAVE_BUTTON_TAG)
        createButton.performClick()

        // then
        val error = MessageSnackBarHub.messages.first()
        assertEquals(unsupportedDaysOfWeekErrorTemplate, error)
    }

    private fun initRepositories() {
        RepositoryProvider.init(DrinkRepositoryTestImpl(), SettingsRepositoryTestImpl())
    }
}