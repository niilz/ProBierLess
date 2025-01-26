package de.niilz.probierless.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = Blue,
    onBackground = OrangeBright,
    primary = Orange,
    //secondary = Color.White,
    onPrimary = BlueBright,
    //onSecondary = BlueBright,
)

private val LightColorScheme = lightColorScheme(
    background = Orange,
    onBackground = Blue,
    primary = BlueBright,
    //secondary = Color.White,
    onPrimary = Orange,
    //onSecondary = BlueBright,

    /* Other default colors to override
      background = Color(0xFFFFFBFE),
      surface = Color(0xFFFFFBFE),
      onTertiary = Color.White,
      onBackground = Color(0xFF1C1B1F),
      onSurface = Color(0xFF1C1B1F),
      */
)

@Composable
fun ProBierLessTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    // TODO: How to actually figure out if dynamicColors is set?
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
