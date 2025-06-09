package de.niilz.probierless.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.niilz.probierless.cross.MessageSnackBarHub
import de.niilz.probierless.ui.components.settings.Settings
import de.niilz.probierless.ui.navigation.EditorRoute
import de.niilz.probierless.ui.navigation.MainViewRoute
import de.niilz.probierless.ui.navigation.SettingsRoute
import de.niilz.probierless.ui.theme.ProBierLessTheme
import kotlinx.coroutines.launch

@Composable
fun AppView() {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ProBierLessTheme {
        val navConrtoller = rememberNavController()
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { innerPadding ->

            ObserveSnackBar(
                MessageSnackBarHub.messages,
                snackbarHostState
            ) { errorEvent ->
                scope.launch {
                    // Should not happen, but who knows
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(errorEvent)
                }
            }
            NavHost(navController = navConrtoller, startDestination = MainViewRoute) {
                composable<MainViewRoute> {
                    MainView(innerPadding, navConrtoller)
                }
                composable<EditorRoute> {
                    MainView(innerPadding, navConrtoller)
                }
                composable<SettingsRoute> {
                    Settings(innerPadding, navConrtoller)
                }
            }
        }
    }
}