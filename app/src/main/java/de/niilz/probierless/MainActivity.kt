package de.niilz.probierless

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.niilz.probierless.dev.preview.addDrinks
import de.niilz.probierless.dev.preview.initDrinkRepositoryForPreview
import de.niilz.probierless.storage.Db
import de.niilz.probierless.tracking.dto.DrinkDto
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.tracking.repository.DrinkRepositoryImpl
import de.niilz.probierless.tracking.repository.DrinkRepositoryProvider
import de.niilz.probierless.ui.components.MainView
import de.niilz.probierless.ui.navigation.EditorRoute
import de.niilz.probierless.ui.navigation.MainViewRoute
import de.niilz.probierless.ui.theme.ProBierLessTheme

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

            ProBierLessTheme {
                val navConrtoller = rememberNavController()
                NavHost(navController = navConrtoller, startDestination = MainViewRoute) {
                    composable<MainViewRoute> {
                        MainView(
                            navigation = { navConrtoller.navigate(route = EditorRoute) }
                        )
                    }
                    composable<EditorRoute> {
                        MainView(
                            editable = true
                        ) { navConrtoller.navigate(route = MainViewRoute) }
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
        MainView(true, navigation = {})
    }
}

