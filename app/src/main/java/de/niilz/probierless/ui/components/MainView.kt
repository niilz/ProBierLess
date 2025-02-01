package de.niilz.probierless.ui.components

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.niilz.probierless.storage.entity.DrinkEntity
import de.niilz.probierless.tracking.viewmodel.DrinkStateViewModel

const val DRINK_INPUT_TAG = "drink-input"
const val ICON_INPUT_TAG = "icon-input"

@Composable
fun MainView(drinkState: Map<String, DrinkEntity>) {
    val drinkStateViewModel = viewModel<DrinkStateViewModel>()
    drinkStateViewModel.init(drinkState)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val drinksMap: Map<String, DrinkEntity> = drinkStateViewModel.drinkState.toMap()
            val drinks: List<DrinkEntity> = drinksMap.values.toList()
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(all = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(drinks) { drink ->
                    DrinkCounter(
                        name = drink.name,
                        icon = drink.icon,
                    )
                }
            }
            DrinkCreator(drinkStateViewModel)
            Button(onClick = { drinkStateViewModel.clearDrinks() }) {
                Text(text = "clear all", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}