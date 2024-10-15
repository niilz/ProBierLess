package de.niilz.probierless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.niilz.probierless.storage.EclipseStore
import de.niilz.probierless.storage.StoreRoot
import de.niilz.probierless.tracking.dto.Drink
import de.niilz.probierless.ui.theme.ProBierLessTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    println("Creating Store")
    val store = EclipseStore(applicationContext).store
    println("starting store")
    store.start()
    println("current root: ${store.root()}")
    println("setting root")
    store.setRoot(StoreRoot())
    println("current root: ${store.root()}")

    println("getting root")
    val root = store.root() as StoreRoot

    println("Adding a drink")
    root.drinks.put("Bier", Drink("Bier", "BierEmoji"))

    println("Storing the drinks")
    store.store(root.drinks);

    enableEdgeToEdge()
    setContent {
      ProBierLessTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Greeting(
            name = "Android",
            modifier = Modifier.padding(innerPadding)
          )
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
