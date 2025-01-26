package de.niilz.probierless.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = Blue,
    onBackground = OrangeBright,
    primary = Orange,
    //secondary = Color.White,
    onPrimary = BlueBright,
    //onSecondary = BlueBright,
    primaryContainer = Orange,
    onPrimaryContainer = BlueBright,
    surface = Color.Black,
    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    background = OrangeBright,
    onBackground = Blue,
    primary = Blue,
    //secondary = Color.White,
    onPrimary = OrangeBright,
    //onSecondary = BlueBright,
    primaryContainer = Blue,
    onPrimaryContainer = OrangeBright,
    surface = Color.White,
    onSurface = Color.Black,

    /* Other default colors to override
      surface = Color(0xFFFFFBFE),
      onTertiary = Color.White,
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
