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
import de.niilz.probierless.ui.data.Drink
import de.niilz.probierless.ui.mapper.fromUi

private const val DRINK_INPUT_TAG = "drink-input"
private const val ICON_INPUT_TAG = "icon-input"
private const val SIZE_INPUT_TAG = "size-input"
private const val SIZE_TYPE_INPUT_TAG = "size-type-input"
private const val VOL_INPUT_TAG = "vol-input"

@Composable
fun Editor(addDrink: (Drink) -> Unit) {

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
            Input("Icon", { newIcon = it }, Modifier.fillMaxWidth(0.3f), ICON_INPUT_TAG)
            Input("Getränkename", { newDrink = it }, testTagName = DRINK_INPUT_TAG)
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Input(
                "Menge",
                { newSize = it },
                keyboardType = KeyboardType.Number,
                modifier = Modifier.fillMaxWidth(.3f),
                testTagName = SIZE_INPUT_TAG
            )
            // FIXME: Replace with dropdown
            Input(
                "Einheit",
                { newSizeType = it },
                modifier = Modifier.fillMaxWidth(.5f),
                testTagName = SIZE_TYPE_INPUT_TAG
            )
            Input(
                "vol.%",
                { newVol = it },
                keyboardType = KeyboardType.Decimal,
                testTagName = VOL_INPUT_TAG
            )
        }
        Button(
            onClick = {
                val drinkSize = fromUi(newSize, newSizeType)
                drinkSize.onSuccess {
                    val drink = Drink(newDrink, newIcon, it, newVol.toFloat())
                    addDrink(drink)
                }
                drinkSize.onFailure {
                }
            },
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.small
                )
                .testTag(ADD_BUTTON_TAG),
        ) {
            Text(text = "add", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}