package de.niilz.probierless.storage

import android.content.Context
import android.util.Log
import org.eclipse.serializer.persistence.binary.exceptions.BinaryPersistenceException
import org.eclipse.store.storage.embedded.configuration.types.EmbeddedStorageConfiguration
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager
import java.io.File

class Db {
    private val storeData: File
    val store: EmbeddedStorageManager by lazy { init() }

    constructor(context: Context) {
        storeData = File(context.filesDir, "store-data")
    }

    constructor(storeDataPath: String) {
        this.storeData = File(storeDataPath)
    }

    private fun init(): EmbeddedStorageManager {
        return EmbeddedStorageConfiguration.Builder()
            .setStorageDirectory(this.storeData.absolutePath)
            .setChannelCount(1)
            .createEmbeddedStorageFoundation()
            .createEmbeddedStorageManager()
    }

    fun launchStore() {
        try {
            store.start()
        } catch (e: BinaryPersistenceException) {
            Log.e(TAG, "Data structure is incompatible. Store gets reset")
            store.setRoot(StoreRoot())
            store.storeRoot()
        }

        val root = store.root()
        if (root == null) {
            Log.d(TAG, "Root is null! Setting a new StoreRoot")
            store.setRoot(StoreRoot())
            store.storeRoot()
        } else {
            Log.d(TAG, "Root store is already present, launching existing store")
        }
    }

    private companion object {
        val TAG: String = Db::class.java.simpleName
    }
}