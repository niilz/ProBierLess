package de.niilz.probierless.ui.components.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import de.niilz.probierless.ui.theme.ProBierLessTheme

const val DAYS_PER_WEEK_TAG = "days_per_week_tag"

@Composable
fun MaxAmountSetting(
    modifier: Modifier = Modifier,
    maxAlcAmountPerDayGram: Int, maxAlcDaysPerWeek: Int
) {
    Column(modifier) {
        Text(
            "Maximale Alkoholmenge",
            modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Row {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("pro Tag:")
                Text(
                    "${maxAlcAmountPerDayGram}g",
                    modifier.fillMaxWidth(.5f),
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.displayLarge.fontSize
                )
            }
            Column {
                Text(
                    "pro Woche:",
                    modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    "${maxAlcDaysPerWeek * maxAlcAmountPerDayGram}g",
                    modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.displayLarge.fontSize
                )
                Text(
                    "(an $maxAlcDaysPerWeek Tagen)",
                    modifier.fillMaxWidth().testTag(DAYS_PER_WEEK_TAG),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MaxAmountSettingPreview() {
    ProBierLessTheme {
        MaxAmountSetting(maxAlcAmountPerDayGram = 5, maxAlcDaysPerWeek = 3)
    }
}
