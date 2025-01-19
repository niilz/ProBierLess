package de.niilz.probierless.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import de.niilz.probierless.tracking.dto.Drink
import de.niilz.probierless.tracking.viewmodel.DrinkStateViewModel

@Composable
fun MainView(drinkState: HashMap<String, Drink>) {
    val drinkStateViewModel = viewModel<DrinkStateViewModel>()
    drinkStateViewModel.init(drinkState)

    var newDrink by remember { mutableStateOf("") }
    var newIcon by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            drinkStateViewModel.drinkState.forEach { drink ->
                Greeting(
                    name = "${drink.key} ${drink.value.icon}",
                    modifier = Modifier.padding(innerPadding)
                )
            }
            TextField(value = newDrink, onValueChange = { newDrink = it })
            TextField(value = newIcon, onValueChange = { newIcon = it })
            Button(onClick = {
                drinkStateViewModel.addDrink(newDrink, newIcon)
            }) {
                Text(text = "createDrink")
            }
            Button(onClick = { drinkStateViewModel.clearDrinks() }) {
                Text(text = "clear all")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
