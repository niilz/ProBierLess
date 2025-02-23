package de.niilz.probierless.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.viewmodel.compose.viewModel
import de.niilz.probierless.cross.ErrorSnackBarHub
import de.niilz.probierless.tracking.viewmodel.DrinkStateViewModel
import de.niilz.probierless.ui.data.Drink
import kotlinx.coroutines.launch

@Composable
fun MainView() {

    val drinkStateViewModel = viewModel<DrinkStateViewModel>()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val drinks: List<Drink> = drinkStateViewModel.drinkState.toList()
            DrinkGrid(drinks)
            Editor(
                drinkStateViewModel::addDrink,
                drinkStateViewModel::clearDrinks
            )
        }
    }
}