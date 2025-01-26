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
import de.niilz.probierless.tracking.dto.Drink
import de.niilz.probierless.ui.components.MainView
import de.niilz.probierless.ui.theme.ProBierLessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val store = Db(applicationContext).store
        store.start()

        var root = store.root()
        if (root == null) {
            Log.d(TAG, "Root is null! Setting a new StoreRoot")
            root = StoreRoot()
            store.setRoot(StoreRoot())
            store.storeRoot()
            root.drinks["Wein"] = Drink("Wein", "WeinEmoji")
            store.store(root.drinks);
        } else {
            Log.d(TAG, "Root store alread present")
        }

        Log.d(TAG, "Now: ${store.root()}")

        enableEdgeToEdge()
        setContent {
            val drinkState = (store.root() as StoreRoot).drinks
            ProBierLessTheme {
                MainView(drinkState)
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
        MainView(mutableMapOf())
    }
}

