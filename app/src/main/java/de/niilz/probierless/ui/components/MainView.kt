package de.niilz.probierless.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.niilz.probierless.cross.ErrorSnackBarHub
import de.niilz.probierless.ui.data.Drink
import kotlinx.coroutines.launch

@Composable
fun MainView(
    drinkState: List<Drink>,
    clearDrinks: () -> Unit,
    addDrink: (Drink) -> Unit
) {

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
            val drinks: List<Drink> = drinkState.toList()
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(all = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(drinks) { drink ->
                    DrinkCounter(drink)
                }
            }
            Editor(addDrink)
            Button(
                onClick = clearDrinks,
                Modifier.background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.small
                )
            ) {
                Text(text = "clear all", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}