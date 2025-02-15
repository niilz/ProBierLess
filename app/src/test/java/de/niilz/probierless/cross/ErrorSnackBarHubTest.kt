package de.niilz.probierless.cross

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ErrorSnackBarHubTest {

    @Test
    fun `error message can be sent into channel`() = runTest {
        // given
        val testError = "Test-Error"
        val initialValue = ErrorSnackBarHub.tryReceive()
        assertNull(initialValue)

        // when
        val sendJob = backgroundScope.launch {
            ErrorSnackBarHub.addError(testError)
        }

        // then
        val evalJob = backgroundScope.launch {
            val sentError = ErrorSnackBarHub.errors.firstOrNull()
            assertEquals(testError, sentError)
        }

        listOf(sendJob, evalJob).forEach { it.join() }

        val noMoreErrors = ErrorSnackBarHub.tryReceive()
        assertNull(noMoreErrors)
    }
}