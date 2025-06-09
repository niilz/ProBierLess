package de.niilz.probierless.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun MyButton(
    text: String,
    handler: () -> Unit,
    modifier: Modifier = Modifier,
    testTagName: String = ""
) {
    Button(
        onClick = handler,
        modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
            .testTag(testTagName),
    ) {
        Text(text, color = MaterialTheme.colorScheme.onPrimary)
    }
}