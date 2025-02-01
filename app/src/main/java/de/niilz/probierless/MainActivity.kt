package de.niilz.probierless

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.niilz.probierless.storage.Db
import de.niilz.probierless.storage.StoreRoot
import de.niilz.probierless.storage.entity.DrinkEntity
import de.niilz.probierless.tracking.repository.DrinkRepository
import de.niilz.probierless.tracking.repository.DrinkRepositoryImpl
import de.niilz.probierless.ui.components.MainView
import de.niilz.probierless.ui.theme.ProBierLessTheme
import org.eclipse.serializer.persistence.binary.exceptions.BinaryPersistenceException

class MainActivity : ComponentActivity() {

    lateinit var drinkRepository: DrinkRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val store = Db(applicationContext).store
        try {
            store.start()
        } catch (e: BinaryPersistenceException) {
            Log.e(TAG, "Data structure is incompatible. Store gets reset")
            store.setRoot(StoreRoot())
        }

        var root = store.root()
        root?.let { it as? StoreRoot }?.let {
            drinkRepository = DrinkRepositoryImpl(it)
        }
        if (root == null) {
            Log.d(TAG, "Root is null! Setting a new StoreRoot")
            root = StoreRoot()
            store.setRoot(StoreRoot())
            store.storeRoot()
            root.drinks.add(DrinkEntity("Wein", "WeinEmoji"))
            store.store(root.drinks);
        } else {
            Log.d(TAG, "Root store alread present")
        }

        Log.d(TAG, "Now: ${store.root()}")

        enableEdgeToEdge()
        setContent {
            ProBierLessTheme {
                MainView()
            }
        }
    }

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProBierLessTheme {
        MainView()
    }
}

