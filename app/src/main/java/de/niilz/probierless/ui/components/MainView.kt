package de.niilz.probierless.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import de.niilz.probierless.cross.ErrorSnackBarHub
import de.niilz.probierless.dev.preview.addDrinks
import de.niilz.probierless.dev.preview.initDrinkRepositoryForPreview
import de.niilz.probierless.tracking.viewmodel.DrinkStateViewModel
import de.niilz.probierless.ui.components.common.MyButton
import de.niilz.probierless.ui.data.Drink
import de.niilz.probierless.ui.theme.ProBierLessTheme
import kotlinx.coroutines.launch

@Composable
fun MainView(editable: Boolean = false, navigation: () -> Unit) {

    val drinkStateViewModel = viewModel<DrinkStateViewModel>()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->

        ObserveSnackBarErrors(ErrorSnackBarHub.errors, snackbarHostState) { errorEvent ->
            scope.launch {
                // Should not happen, but who knows
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(errorEvent)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val drinks: List<Drink> = drinkStateViewModel.drinkState.orEmpty()

            DrinkGrid(
                Modifier.fillMaxHeight(.65f),
                drinks,
                drinkStateViewModel::deleteDrink
            )

            if (editable) {
                Editor(
                    modifier = Modifier
                        .fillMaxWidth(.9f),
                    drinkStateViewModel::addDrink,
                    drinkStateViewModel::clearDrinks,
                    navigateToMainView = navigation
                )
            } else {
                MyButton("Anpassen", navigation)
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
        MainView(true, navigation = {})
    }
}

