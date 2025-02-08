package de.niilz.probierless.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun Input(onUpdate: (String) -> Unit, modifier: Modifier = Modifier, testTagName: String = "") {

    var value by remember { mutableStateOf("") }

    OutlinedTextField(
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .testTag(testTagName)
            .background(color = MaterialTheme.colorScheme.surfaceVariant),
        value = value,
        onValueChange = {
            value = it
            onUpdate(value)
        }
    )
}