package de.niilz.probierless.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import de.niilz.probierless.cross.MessageSnackBarHub
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
    modifier: Modifier = Modifier,
    addDrink: (Drink) -> Unit,
    clearDrinks: () -> Unit,
    navigateToMainView: () -> Unit,
) {

    var newDrink = ""
    var newIcon = ""
    var newSize = ""
    var newSizeType = ""
    var newVol = ""

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyInput(
                    Modifier.weight(1f),
                    "Icon",
                    { newIcon = it },
                    textStyle = MaterialTheme.typography.displayLarge,
                    testTagName = ICON_INPUT_TAG
                )
                MyInput(
                    Modifier
                        .weight(2.5f)
                        .fillMaxHeight(),
                    label = "Getränkename",
                    onUpdate = { newDrink = it },
                    testTagName = DRINK_INPUT_TAG
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // TODO: Replace with dropdown
                MyInput(
                    modifier = Modifier.weight(1f),
                    "Einheit",
                    { newSizeType = it },
                    testTagName = SIZE_TYPE_INPUT_TAG
                )
                MyInput(
                    modifier = Modifier.weight(1f),
                    "Menge",
                    { newSize = it },
                    keyboardType = KeyboardType.Number,
                    testTagName = SIZE_INPUT_TAG
                )
                MyInput(
                    modifier = Modifier.weight(1f),
                    label = "vol.%",
                    onUpdate = { newVol = it },
                    keyboardType = KeyboardType.Decimal,
                    testTagName = VOL_INPUT_TAG
                )
            }

            Spacer(Modifier.height(10.dp))

            fun addDrinkButtonHandler() {
                val drinkSize = fromUi(newSize, newSizeType)
                drinkSize.onSuccess {
                    val drink = Drink(newDrink, newIcon, it, newVol.toFloat())
                    addDrink(drink)
                }
                drinkSize.onFailure {
                    scope.launch {
                        MessageSnackBarHub.addMessage(it.message ?: "no-message")
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

        Spacer(Modifier.height(10.dp))

        MyButton("Home", navigateToMainView)
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ProBierLessTheme {
        Scaffold { padding ->
            Editor(Modifier.padding(padding), {}, {}, {})
        }
    }
}
