package de.niilz.probierless.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DrinkCounter(name: String, modifier: Modifier) {
    Text(text = name, modifier = modifier)
}