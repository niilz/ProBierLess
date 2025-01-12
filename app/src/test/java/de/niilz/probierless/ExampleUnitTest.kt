package de.niilz.probierless

import de.niilz.probierless.storage.EclipseStore
import de.niilz.probierless.storage.StoreRoot
import de.niilz.probierless.tracking.dto.Drink
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.BeforeClass
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    companion object {

        private const val TEST_DATA_PATH = "./tmp/test-data";
        private const val TEST_NAME = "test-name"

        @JvmStatic
        @BeforeClass
        fun init() {
            val store = launchDb()
            val storeRoot = StoreRoot()
            store.setRoot(storeRoot)
            store.storeRoot()

            storeRoot.drinks[TEST_NAME] = Drink("foo", "icon")
            store.store(storeRoot.drinks)

            store.shutdown()
        }

        @JvmStatic
        @AfterClass
        fun cleanup() {
            deleteTestData(TEST_DATA_PATH)
        }

        private fun deleteTestData(path: String) {
            File(path).listFiles()?.forEach {
                if (it.isDirectory) {
                    println("Dir: ${it.path}")
                    deleteTestData(it.path)
                } else {
                    println("file: ${it.path}")
                    it.delete()
                }
            }
            Files.delete(Path.of(path))
        }

        private fun launchDb(): EmbeddedStorageManager {
            val db = EclipseStore(TEST_DATA_PATH)
            val store = db.store
            store.start()
            return store
        }
    }

    @Test
    fun dataLoads() {
        when (val storeRoot = launchDb().root()) {
            is StoreRoot -> {
                val testData = storeRoot.drinks[TEST_NAME]
                assertNotNull(testData)
                assertEquals("foo", testData?.name)
            }

            else -> fail("storeRoot is not a StoreRoot")
        }
    }
}
