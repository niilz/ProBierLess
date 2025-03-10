package de.niilz.probierless.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.niilz.probierless.ui.theme.ProBierLessTheme

@Composable
fun MyInput(
    modifier: Modifier = Modifier,
    label: String = "",
    onUpdate: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    textStyle: TextStyle = MaterialTheme.typography.displayMedium,
    testTagName: String = "",
) {

    var value by remember { mutableStateOf("") }

    // TODO: align label and text horizontally.center
    OutlinedTextField(
        label = { Text(label, Modifier.wrapContentHeight(Alignment.CenterVertically)) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        value = value,
        textStyle = textStyle,
        modifier = modifier
            .testTag(testTagName)
            .background(color = MaterialTheme.colorScheme.surfaceVariant),
        onValueChange = {
            value = it
            onUpdate(value)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ProBierLessTheme {
        Column {
            MyInput(onUpdate = {})
            MyInput(label = "Label", onUpdate = {})
            MyInput(Modifier.height(100.dp), label = "Label", onUpdate = {})
        }
    }
}
