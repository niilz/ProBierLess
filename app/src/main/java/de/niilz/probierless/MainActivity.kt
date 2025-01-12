package de.niilz.probierless

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.niilz.probierless.storage.Db
import de.niilz.probierless.storage.StoreRoot
import de.niilz.probierless.tracking.dto.Drink
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

        val drinks = (store.root() as StoreRoot).drinks

        enableEdgeToEdge()
        setContent {
            ProBierLessTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        drinks.forEach { drink ->
                            Greeting(
                                name = "${drink.key} ${drink.value.icon}",
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProBierLessTheme {
        Greeting("Android")
    }
}

