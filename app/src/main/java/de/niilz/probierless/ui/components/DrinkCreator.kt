package de.niilz.probierless.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import de.niilz.probierless.tracking.viewmodel.DrinkStateViewModel

@Composable
fun DrinkCreator(drinkStateViewModel: DrinkStateViewModel) {

    var newDrink by remember { mutableStateOf("") }
    var newIcon by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .testTag(DRINK_INPUT_TAG),
                value = newDrink,
                onValueChange = { newDrink = it })
            OutlinedTextField(
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(ICON_INPUT_TAG),
                value = newIcon,
                onValueChange = { newIcon = it })
        }
        Button(onClick = {
            drinkStateViewModel.addDrink(newDrink, newIcon)
        }) {
            Text(text = "createDrink", color = MaterialTheme.colorScheme.onSecondary)
        }
    }
}