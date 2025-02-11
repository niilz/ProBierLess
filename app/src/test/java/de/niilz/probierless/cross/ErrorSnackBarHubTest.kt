package de.niilz.probierless.cross

import kotlinx.coroutines.test.runTest
import org.junit.Test

class ErrorSnackBarHubTest {

    @Test
    fun sendAndReceiveSnackbarErrorMessage() = runTest {
        val testError = "Test-Error"
        // given
        ErrorSnackBarHub.addError(testError)
        //assertTrue(ErrorSnackBarHub.errors.last().isEmpty())

        //// when
        //runBlocking {
        //    ErrorSnackBarHub.addError(testError)
        //}

        //// then
        //runBlocking {
        //    val withError = ErrorSnackBarHub.errors.toList()
        //    assertEquals(1, withError.size)
        //    assertEquals(testError, withError[0])
        //}
    }
}