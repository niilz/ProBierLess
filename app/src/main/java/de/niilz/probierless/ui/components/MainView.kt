package de.niilz.probierless.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import de.niilz.probierless.dev.preview.addDrinks
import de.niilz.probierless.dev.preview.initDrinkRepositoryForPreview
import de.niilz.probierless.tracking.viewmodel.DrinkStateViewModel
import de.niilz.probierless.ui.components.common.MyButton
import de.niilz.probierless.ui.navigation.NavControllerManager
import de.niilz.probierless.ui.navigation.UiState
import de.niilz.probierless.ui.navigation.UiStateEnum
import de.niilz.probierless.ui.theme.ProBierLessTheme

@Composable
fun MainView(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavHostController? = null
) {

    val drinkStateViewModel = viewModel<DrinkStateViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val drinks by drinkStateViewModel.drinkState.collectAsState()

        DrinkGrid(
            Modifier.fillMaxHeight(.65f),
            drinks,
            drinkStateViewModel::countDrink,
            drinkStateViewModel::deleteDrink
        )

        if (UiState.state === UiStateEnum.EDITOR) {
            Editor(
                modifier = Modifier
                    .fillMaxWidth(.9f),
                drinkStateViewModel::addDrink,
                drinkStateViewModel::clearDrinks,
                navigateToMainView = {
                    NavControllerManager.navigateTo(
                        UiStateEnum.MAIN,
                        navController
                    )
                },
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(.9f),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MyButton(
                    "Anpassen",
                    { NavControllerManager.navigateTo(UiStateEnum.EDITOR, navController) })
                MyButton("Reset", drinkStateViewModel::resetCounts)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    initDrinkRepositoryForPreview()
    addDrinks(10)
    ProBierLessTheme {
        MainView()
    }
}

