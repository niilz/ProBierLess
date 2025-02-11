package de.niilz.probierless.cross

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object ErrorSnackBarHub {
    private var _errors = Channel<String>()
    val errors = _errors.receiveAsFlow()

    suspend fun addError(snackBarError: String) {
        _errors.send(snackBarError)
    }
}