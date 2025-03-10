package de.niilz.probierless.ui.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import de.niilz.probierless.tracking.dto.Ml
import de.niilz.probierless.ui.data.Drink

@Composable
fun DrinkCounter(drink: Drink, modifier: Modifier = Modifier) {
    Card(
        onClick = { println("clicked") },
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        // TODO: Do not have these hardcoded sizes, use Material.typography
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
            Text(text = drink.name, modifier = modifier, fontSize = TextUnit(20f, TextUnitType.Sp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrinkCounterPreview() {
    val drink = Drink("Bier", "\uD83E\uDD43", Ml(330), 17.8f)
    DrinkCounter(drink)
}