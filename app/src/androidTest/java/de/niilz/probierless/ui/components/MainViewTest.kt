package de.niilz.probierless.ui.components

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import de.niilz.probierless.dev.preview.DrinkRepositoryTestImpl
import de.niilz.probierless.dev.preview.SettingsRepositoryTestImpl
import de.niilz.probierless.storage.entity.DrinkEntity
import de.niilz.probierless.tracking.dto.L
import de.niilz.probierless.tracking.repository.RepositoryProvider
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

    @get:Rule
    val rule = createComposeRule()

    @Before
    fun setup() {
        initRepositories()
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
        rule.setContent { MainView() }

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
    fun deletingDrinkRemovesDrinkCounter() = runTest {
        // given
        val repo = RepositoryProvider.getDrinkRepository()!!
        repo.addDrink(DrinkEntity("test-drink", "test-icon", L(0.5f), 0.5f))
        assertEquals(1, repo.fetchAllDrinks().size)

        rule.setContent {
            MainView()
        }

        val removeBeerButtonX = rule.onNodeWithText("❌")
        removeBeerButtonX.assertExists()

        // when
        removeBeerButtonX.performClick()

        // then
        assertTrue(repo.fetchAllDrinks().isEmpty())
    }

    @Test
    fun countingDrinkIncrementsCountValue() = runTest {
        // given
        UiState.state = UiStateEnum.MAIN
        val repo = RepositoryProvider.getDrinkRepository()!!
        val drinkName = "test-drink"
        repo.addDrink(DrinkEntity(drinkName, "test-icon", L(0.5f), 0.5f))
        assertEquals(1, repo.fetchAllDrinks().size)

        rule.setContent {
            ProBierLessTheme { MainView() }
        }

        val testDrinkButton = rule.onNodeWithTag("drink-counter-1")
        testDrinkButton.assertExists()
        testDrinkButton.assertTextContains("0")

        // when - click one
        testDrinkButton.performClick()
        // then - 1
        testDrinkButton.assertTextContains("1")
        assertEquals(1, repo.fetchAllDrinks()[1]?.count)

        // when - click two
        testDrinkButton.performClick()
        // then - 2
        testDrinkButton.assertTextContains("2")
        assertEquals(2, repo.fetchAllDrinks()[1]?.count)
    }

    @Test
    fun resettingCounterWorks() = runTest {
        // given
        UiState.state = UiStateEnum.MAIN
        val repo = RepositoryProvider.getDrinkRepository()!!
        val drinkName = "test-drink"
        repo.addDrink(DrinkEntity(drinkName, "test-icon", L(0.5f), 0.5f, 42))
        repo.addDrink(DrinkEntity(drinkName, "test-icon", L(0.5f), 0.5f, 43))
        assertEquals(2, repo.fetchAllDrinks().size)

        rule.setContent {
            ProBierLessTheme { MainView() }
        }

        val testDrinkButtons = rule.onAllNodesWithText(drinkName)
        testDrinkButtons.assertCountEquals(2)
        val buttonOne = testDrinkButtons[0]
        buttonOne.assertTextContains("42")
        val buttonTwo = testDrinkButtons[1]
        buttonTwo.assertTextContains("43")

        val resetButton = rule.onNodeWithText("Reset")
        resetButton.assertExists()

        // when - click one
        resetButton.performClick()

        // then
        buttonOne.assertTextContains("0")
        assertEquals(0, repo.fetchAllDrinks()[1]?.count)

        buttonTwo.assertTextContains("0")
        assertEquals(0, repo.fetchAllDrinks()[2]?.count)
    }

    private fun initRepositories() {
        RepositoryProvider.init(DrinkRepositoryTestImpl(), SettingsRepositoryTestImpl())
    }

    companion object {
        private const val ADD_TEXT = "Hinzufügen"
    }
}
