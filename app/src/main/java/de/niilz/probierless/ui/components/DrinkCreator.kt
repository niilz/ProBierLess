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

@Composable
fun DrinkCreator(addDrink: (String, String) -> Unit) {

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
                    .fillMaxWidth(0.3f)
                    .testTag(ICON_INPUT_TAG),
                value = newIcon,
                onValueChange = { newIcon = it })
            OutlinedTextField(
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(DRINK_INPUT_TAG),
                value = newDrink,
                onValueChange = { newDrink = it })
        }
        Button(onClick = {
            addDrink(newDrink, newIcon)
        }) {
            Text(text = "createDrink", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}