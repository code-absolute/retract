package tech.codeabsolute.retract.proximityservice.domain.usescases

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.codeabsolute.retract.home.presentation.reducer.HomeScreenIntent
import tech.codeabsolute.retract.proximityservice.data.repositories.ProximityServiceRepository

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class StopProximityServiceUseCaseTest {

	@MockK
	private lateinit var proximityServiceRepository: ProximityServiceRepository

	private lateinit var useCase: StopProximityServiceUseCase

	@BeforeEach
	fun setUp() {

		useCase = StopProximityServiceUseCase(proximityServiceRepository)
	}


	@Test
	fun `Emit intent Error if service does not stop correctly`() = runTest {

		every { proximityServiceRepository.stopService() } returns false

		val intent = useCase().firstOrNull()

		assertEquals(HomeScreenIntent.Error, intent)
	}

	@Test
	fun `Emit intent ServiceStopped if service stops correctly`() = runTest {

		every { proximityServiceRepository.stopService() } returns true

		val intent = useCase().firstOrNull()

		assertEquals(HomeScreenIntent.ServiceStopped, intent)
	}
}