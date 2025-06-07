package de.niilz.probierless.ui.components.common

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

object MyAutoSizeTextDefaults {
    val defaultMaxFontSize: TextUnit = 20.sp
    val defaultMinFontSize: TextUnit = 10.sp
}

@Composable
fun MyAutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    maxFontSize: TextUnit = MyAutoSizeTextDefaults.defaultMaxFontSize,
    minFontSize: TextUnit = MyAutoSizeTextDefaults.defaultMinFontSize,
    stepSize: TextUnit = 2.sp,
) {
    BasicText(
        text = text,
        modifier = modifier,
        maxLines = 1,
        autoSize = TextAutoSize.StepBased(
            maxFontSize = maxFontSize,
            minFontSize = minFontSize,
            stepSize = stepSize
        ),
        style = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
    )
}