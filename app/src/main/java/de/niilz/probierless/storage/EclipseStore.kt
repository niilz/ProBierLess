package de.niilz.probierless.storage

import android.content.Context
import org.eclipse.store.storage.embedded.configuration.types.EmbeddedStorageConfiguration
import java.io.File

class EclipseStore(context: Context) {

  private val storeData = File(context.filesDir, "store-data")

  val store = EmbeddedStorageConfiguration.Builder()
    .setStorageDirectory(storeData.absolutePath)
    .setChannelCount(1)
    .createEmbeddedStorageFoundation()
    .createEmbeddedStorageManager()
}
