package de.niilz.probierless.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.niilz.probierless.cross.ErrorSnackBarHub
import de.niilz.probierless.ui.components.common.MyButton
import de.niilz.probierless.ui.components.common.MyInput
import de.niilz.probierless.ui.data.Drink
import de.niilz.probierless.ui.mapper.fromUi
import de.niilz.probierless.ui.theme.ProBierLessTheme
import kotlinx.coroutines.launch

const val DRINK_INPUT_TAG = "drink-input"
const val ICON_INPUT_TAG = "icon-input"
const val SIZE_INPUT_TAG = "size-input"
const val SIZE_TYPE_INPUT_TAG = "size-type-input"
const val VOL_INPUT_TAG = "vol-input"
const val ADD_BUTTON_TAG = "add-button-input"

@Composable
fun Editor(
    addDrink: (Drink) -> Unit,
    clearDrinks: () -> Unit,
    navigateToMainView: () -> Unit,
    modifier: Modifier = Modifier
) {

    var newDrink = ""
    var newIcon = ""
    var newSize = ""
    var newSizeType = ""
    var newVol = ""

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                MyInput(
                    Modifier
                        .fillMaxWidth(0.3f),
                    "Icon",
                    { newIcon = it },
                    textStyle = MaterialTheme.typography.displayLarge,
                    testTagName = ICON_INPUT_TAG
                )
                MyInput(
                    label = "Getränkename",
                    onUpdate = { newDrink = it },
                    testTagName = DRINK_INPUT_TAG
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                MyInput(
                    modifier = Modifier.fillMaxWidth(.3f),
                    "Menge",
                    { newSize = it },
                    keyboardType = KeyboardType.Number,
                    testTagName = SIZE_INPUT_TAG
                )
                // TODO: Replace with dropdown
                MyInput(
                    modifier = Modifier.fillMaxWidth(.5f),
                    "Einheit",
                    { newSizeType = it },
                    testTagName = SIZE_TYPE_INPUT_TAG
                )
                MyInput(
                    label = "vol.%",
                    onUpdate = { newVol = it },
                    keyboardType = KeyboardType.Decimal,
                    testTagName = VOL_INPUT_TAG
                )
            }
            fun addDrinkButtonHandler() {
                val drinkSize = fromUi(newSize, newSizeType)
                drinkSize.onSuccess {
                    val drink = Drink(newDrink, newIcon, it, newVol.toFloat())
                    addDrink(drink)
                }
                drinkSize.onFailure {
                    scope.launch {
                        ErrorSnackBarHub.addError(it.message ?: "no-message")
                    }
                }
            }
            Row {
                MyButton(
                    "Hinzufügen",
                    { addDrinkButtonHandler() },
                    Modifier
                        .testTag(ADD_BUTTON_TAG)
                        .padding(horizontal = 3.dp)
                )
                MyButton("alle löschen", clearDrinks)
            }
        }
        MyButton("Home", navigateToMainView)
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ProBierLessTheme {
        Scaffold { padding ->
            Editor({}, {}, {}, Modifier.padding(padding))
        }
    }
}
