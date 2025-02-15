package de.niilz.probierless.cross

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object ErrorSnackBarHub {
    private var _errors = Channel<String>()
    val errors = _errors.receiveAsFlow()

    suspend fun addError(snackBarError: String) {
        _errors.send(snackBarError)
    }

    @VisibleForTesting
    fun tryReceive(): String? {
        return _errors.tryReceive().getOrNull()
    }
}