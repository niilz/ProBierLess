package de.niilz.probierless.storage

import android.content.Context
import org.eclipse.store.storage.embedded.configuration.types.EmbeddedStorageConfiguration
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager
import java.io.File

class EclipseStore {
    private val storeData: File
    val store: EmbeddedStorageManager

    constructor(context: Context) {
        storeData = File(context.filesDir, "store-data")
        store = init()
    }

    constructor(storeDataPath: String) {
        this.storeData = File(storeDataPath)
        store = init()
    }

    private fun init(): EmbeddedStorageManager {
        return EmbeddedStorageConfiguration.Builder()
            .setStorageDirectory(this.storeData.absolutePath)
            .setChannelCount(1)
            .createEmbeddedStorageFoundation()
            .createEmbeddedStorageManager()
    }
}
