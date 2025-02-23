package de.niilz.probierless.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun MyInput(
    label: String = "",
    onUpdate: (String) -> Unit,
    modifier: Modifier = Modifier,
    testTagName: String = "",
    keyboardType: KeyboardType = KeyboardType.Text
) {

    var value by remember { mutableStateOf("") }

    OutlinedTextField(
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        value = value,
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .testTag(testTagName)
            .background(color = MaterialTheme.colorScheme.surfaceVariant),
        onValueChange = {
            value = it
            onUpdate(value)
        }
    )
}