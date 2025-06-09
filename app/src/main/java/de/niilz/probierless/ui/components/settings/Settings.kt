package de.niilz.probierless.ui.components.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import de.niilz.probierless.dev.preview.initRepositoriesForPreview
import de.niilz.probierless.tracking.repository.RepositoryProvider
import de.niilz.probierless.tracking.viewmodel.SettingsViewModel
import de.niilz.probierless.ui.components.DrinkGrid
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
            "Wie viele Getränke möchtest du dir pro Tag erlauben?\n(Klicke auf die Counter)",
            Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.Center
        )
        MaxAmountSetting(
            maxAlcAmountPerDayGram = settingsViewModel.readAlcoholDayLimitGram(),
            maxAlcDaysPerWeek = settingsViewModel.readMaxDaysPerWeek()
        )
        DrinkGrid(
            drinks = drinks,
            countDrink = {
                settingsViewModel::addDrinkToDayLimit
            },
            deleteDrink = { UnsupportedOperationException("Restoring alcohol per drink is not supported") },
        )
        var maxDaysPerWeek = ""
        MyInput(
            label = "Tage pro Woche",
            onUpdate = { maxDaysPerWeek = it })
        // Display drink grid without any counts
        Row(
            modifier = Modifier.fillMaxWidth(.9f),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            MyButton("Zurück", { NavControllerManager.navigateTo(UiStateEnum.MAIN, navController) })
            MyButton("Reset", settingsViewModel::resetAlcoholDayLimit)
            MyButton("Speichern", {
                settingsViewModel.writeMaxDaysPerWeek(maxDaysPerWeek)
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    initRepositoriesForPreview()
    RepositoryProvider.getSettingsRepository()!!.storeAlcoholDayLimitGram(5)
    RepositoryProvider.getSettingsRepository()!!.storeMaxDaysPerWeek(3)
    addDrinks(6)
    ProBierLessTheme {
        Settings()
    }
}

