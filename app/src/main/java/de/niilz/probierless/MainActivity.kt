package de.niilz.probierless

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import de.niilz.probierless.storage.Db
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.tracking.repository.DrinkRepositoryImpl
import de.niilz.probierless.tracking.viewmodel.DrinkStateViewModel
import de.niilz.probierless.ui.components.MainView
import de.niilz.probierless.ui.theme.ProBierLessTheme

class MainActivity : ComponentActivity() {

    lateinit var drinkRepository: DrinkRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Log.d(TAG, "setting up Db")
        val db = Db(applicationContext)
        db.launchStore()
        Log.d(TAG, "setting up DrinkRepo")
        drinkRepository = DrinkRepositoryImpl(db.store)

        enableEdgeToEdge()
        setContent {
            ProBierLessTheme {
                val drinkStateViewModel =
                    viewModel<DrinkStateViewModel>(factory = DrinkStateViewModel.Factory)
                MainView(
                    drinkStateViewModel.drinkState,
                    drinkStateViewModel::clearDrinks,
                    drinkStateViewModel::addDrink
                )
            }
        }
    }

    private companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProBierLessTheme {
        MainView(
            listOf(),
            {},
            { _ -> println("Not used in preview") }
        )
    }
}

