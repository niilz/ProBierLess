package de.niilz.probierless.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.niilz.probierless.dev.preview.addDrinks
import de.niilz.probierless.dev.preview.initDrinkRepositoryForPreview
import de.niilz.probierless.ui.components.common.MyInput
import de.niilz.probierless.ui.theme.ProBierLessTheme

@Composable
fun Settings(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavHostController? = null
) {
    //val settingsViewModel = viewModel<SettingsViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Wähle wie viele Getränke du pro Tag trinken möchtest")
        MyInput(label = "Tage pro Woche", onUpdate = {/* TODO */ })
        // Display drink grid without any counts
        // Display total amount of alkohol allowed
        // Instruction:
        // Wähle an wie vielen Tagen du maximal dies Menge trinken möchtest
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

