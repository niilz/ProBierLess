package de.niilz.probierless.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.niilz.probierless.tracking.dto.Ml
import de.niilz.probierless.ui.data.Drink

@Composable
fun DrinkGrid(
    modifier: Modifier = Modifier,
    drinks: Map<Int, Drink>,
    countDrink: (Int) -> Unit,
    deleteDrink: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxHeight(.65f),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(all = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(drinks.entries.toList()) { drink ->
            DrinkCounter(
                drinkEntry = drink,
                countDrink = countDrink,
                deleteDrink = deleteDrink
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrinkGridPreview() {
    val drinks = (0..40).associate {
        Pair(it, Drink("Bier", "\uD83C\uDF7A", Ml(330), 4.9f))
    }
    DrinkGrid(drinks = drinks, countDrink = {}, deleteDrink = {})
}
