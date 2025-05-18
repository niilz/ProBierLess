package de.niilz.probierless.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import de.niilz.probierless.dev.preview.addDrinks
import de.niilz.probierless.dev.preview.initDrinkRepositoryForPreview
import de.niilz.probierless.tracking.viewmodel.SettingsViewModel
import de.niilz.probierless.ui.components.common.MyButton
import de.niilz.probierless.ui.components.common.MyInput
import de.niilz.probierless.ui.navigation.NavControllerManager
import de.niilz.probierless.ui.navigation.UiStateEnum
import de.niilz.probierless.ui.theme.ProBierLessTheme

@Composable
fun Settings(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavHostController? = null
) {
    val settingsViewModel = viewModel<SettingsViewModel>()
    val drinks by settingsViewModel.settingsState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Wähle wie viele Getränke du pro Tag trinken möchtest\n(Klicke auf die Counter)",
            Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.Center
        )
        DrinkGrid(
            drinks = drinks!!,
            countDrink = TODO(),
            deleteDrink = TODO(),
        )
        MyInput(label = "Tage pro Woche", onUpdate = {/* TODO */ })
        // Display drink grid without any counts
        // Display total amount of alkohol allowed
        // Instruction:
        // Wähle an wie vielen Tagen du maximal dies Menge trinken möchtest
        MyButton("Zurück", { NavControllerManager.navigateTo(UiStateEnum.MAIN, navController) })
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    initDrinkRepositoryForPreview()
    addDrinks(6)
    ProBierLessTheme {
        Settings()
    }
}

