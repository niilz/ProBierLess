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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@Composable
fun DrinkCounter(name: String, icon: String, modifier: Modifier = Modifier) {
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
            // FIXME: Get the real values for size and vol
            Row {
                Text(text = "330ml")
                Text(text = "4.9")
            }
            Text(
                text = icon,
                modifier = modifier.padding(10.dp),
                fontSize = TextUnit(40f, TextUnitType.Sp)
            )
            Text(text = name, modifier = modifier, fontSize = TextUnit(20f, TextUnitType.Sp))
        }
    }
}