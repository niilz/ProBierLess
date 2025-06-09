package de.niilz.probierless.cross

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

private val TAG = MessageSnackBarHub::class.java.simpleName

object MessageSnackBarHub {
    private var _messages = Channel<String>()
    val messages = _messages.receiveAsFlow()

    suspend fun addMessage(snackBarMessage: String) {
        //Log.d(TAG, "Adding message to snackbar: $snackBarMessage")
        println("Adding message to snackbar: $snackBarMessage")
        _messages.send(snackBarMessage)
    }

    @VisibleForTesting
    fun tryReceive(): String? {
        return _messages.tryReceive().getOrNull()
    }

}