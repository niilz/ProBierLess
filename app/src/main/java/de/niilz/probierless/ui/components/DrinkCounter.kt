package de.niilz.probierless.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import de.niilz.probierless.cross.MessageSnackBarHub
import de.niilz.probierless.tracking.dto.Ml
import de.niilz.probierless.ui.data.Drink
import de.niilz.probierless.ui.navigation.UiState
import de.niilz.probierless.ui.navigation.UiStateEnum
import kotlinx.coroutines.launch

@Composable
fun DrinkCounter(
    modifier: Modifier = Modifier,
    drinkEntry: Map.Entry<Int, Drink>,
    countDrink: (Int) -> Unit,
    deleteDrink: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    fun handleCountDrink() {
        val drinkId = drinkEntry.key
        countDrink(drinkId)
        scope.launch {
            MessageSnackBarHub.addMessage("Ein ${drinkEntry.value.name} gezählt")
        }
    }

    Card(
        onClick = { handleCountDrink() },
        modifier = modifier.testTag("drink-counter-${drinkEntry.key}"),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        // TODO: Do not have these hardcoded sizes, use Material.typography
        val drink = drinkEntry.value
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (UiState.state === UiStateEnum.EDITOR) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .clickable { deleteDrink(drinkEntry.key) },
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text("❌", fontSize = TextUnit(20f, TextUnitType.Sp))
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${drink.drinkSize}")
                Text(text = "${drink.vol}‰")
            }
            Text(
                text = drink.icon,
                modifier = modifier.padding(10.dp),
                fontSize = TextUnit(40f, TextUnitType.Sp)
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${drink.count}",
                    modifier = modifier,
                    fontSize = TextUnit(20f, TextUnitType.Sp)
                )
                Text(
                    text = drink.name,
                    modifier = modifier,
                    fontSize = TextUnit(20f, TextUnitType.Sp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrinkCounterPreview() {
    val drink =
        mapOf(Pair(42, Drink("Bier", "\uD83E\uDD43", Ml(330), 17.8f))).entries.iterator().next()
    DrinkCounter(
        drinkEntry = drink, countDrink = {}, deleteDrink = {}
    )
}