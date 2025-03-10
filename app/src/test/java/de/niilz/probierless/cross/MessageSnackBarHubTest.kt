package de.niilz.probierless.cross

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MessageSnackBarHubTest {

    @Test
    fun `error message can be sent into channel`() = runTest {
        // given
        val testError = "Test-Error"
        val initialValue = MessageSnackBarHub.tryReceive()
        assertNull(initialValue)

        // when
        val sendJob = backgroundScope.launch {
            MessageSnackBarHub.addMessage(testError)
        }

        // then
        val evalJob = backgroundScope.launch {
            val sentError = MessageSnackBarHub.messages.firstOrNull()
            assertEquals(testError, sentError)
        }

        listOf(sendJob, evalJob).forEach { it.join() }

        val noMoreErrors = MessageSnackBarHub.tryReceive()
        assertNull(noMoreErrors)
    }
}