package de.niilz.probierless.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import de.niilz.probierless.tracking.dto.DrinkSize
import de.niilz.probierless.ui.components.common.Input
import de.niilz.probierless.ui.mapper.fromUi

private const val DRINK_INPUT_TAG = "drink-input"
private const val ICON_INPUT_TAG = "icon-input"
private const val SIZE_INPUT_TAG = "size-input"
private const val SIZE_TYPE_INPUT_TAG = "size-type-input"
private const val VOL_INPUT_TAG = "vol-input"

@Composable
fun DrinkCreator(addDrink: (String, String, DrinkSize, Float) -> Unit) {

    var newDrink = ""
    var newIcon = ""
    var newSize = ""
    var newSizeType = ""
    var newVol = ""


    Column(
        modifier = Modifier.fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Input({ newIcon = it }, Modifier.fillMaxWidth(0.3f), ICON_INPUT_TAG)
            Input({ newDrink = it }, testTagName = DRINK_INPUT_TAG)
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Input(
                { newSize = it },
                keyboardType = KeyboardType.Number,
                modifier = Modifier.fillMaxWidth(.3f),
                testTagName = SIZE_INPUT_TAG
            )
            // FIXME: Replace with dropdown
            Input(
                { newSizeType = it },
                modifier = Modifier.fillMaxWidth(0.3f),
                testTagName = SIZE_TYPE_INPUT_TAG
            )
            Input(
                { newVol = it },
                keyboardType = KeyboardType.Decimal,
                testTagName = VOL_INPUT_TAG
            )
        }
        Button(
            onClick = {
                // FIXME: Assign actual values
                addDrink(newDrink, newIcon, fromUi(newSize, newSizeType), newVol.toFloat())
            }, modifier = Modifier.background(
                MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
        ) {
            Text(text = "createDrink", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}