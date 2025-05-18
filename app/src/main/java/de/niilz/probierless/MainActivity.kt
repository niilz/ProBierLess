package de.niilz.probierless

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.niilz.probierless.cross.MessageSnackBarHub
import de.niilz.probierless.dev.preview.addDrinks
import de.niilz.probierless.storage.Db
import de.niilz.probierless.tracking.repository.DrinkRepositoryImpl
import de.niilz.probierless.tracking.repository.DrinkRepositoryProvider
import de.niilz.probierless.ui.components.MainView
import de.niilz.probierless.ui.components.ObserveSnackBar
import de.niilz.probierless.ui.navigation.EditorRoute
import de.niilz.probierless.ui.navigation.MainViewRoute
import de.niilz.probierless.ui.navigation.UiState
import de.niilz.probierless.ui.navigation.UiStateEnum
import de.niilz.probierless.ui.theme.ProBierLessTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "setting up Db")
        val db = Db(applicationContext)
        db.launchStore()
        Log.d(TAG, "setting up DrinkRepo")

        DrinkRepositoryProvider.init(DrinkRepositoryImpl(db.store))


        enableEdgeToEdge()
        setContent {

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
                            MainView(innerPadding) {
                                UiState.state = UiStateEnum.EDITOR
                                navConrtoller.navigate(route = EditorRoute)
                            }
                        }
                        composable<EditorRoute> {
                            MainView(innerPadding) {
                                UiState.state = UiStateEnum.MAIN
                                navConrtoller.navigate(route = MainViewRoute)
                                innerPadding
                            }
                        }
                    }
                }
            }
        }
    }

    private companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    addDrinks(10)
    ProBierLessTheme {
        MainView(PaddingValues()) {}
    }
}

