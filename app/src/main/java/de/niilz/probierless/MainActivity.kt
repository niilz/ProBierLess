package de.niilz.probierless

import android.os.Bundle
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
            println("Root is null! Setting a new StoreRoot")
            root = StoreRoot()
            store.setRoot(StoreRoot())
            store.storeRoot()
            root.drinks["Wein"] = Drink("Wein", "WeinEmoji")
            store.store(root.drinks);
        } else {
            println("Root store alread present")
        }

        println("Now: ${store.root()}")

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
