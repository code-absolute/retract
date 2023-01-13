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
internal class IsProximityServiceRunningUseCaseTest {

	@MockK
	private lateinit var proximityServiceRepository: ProximityServiceRepository

	private lateinit var useCase: IsProximityServiceRunningUseCase

	@BeforeEach
	fun setUp() {

		useCase = IsProximityServiceRunningUseCase(proximityServiceRepository)
	}

	@Test
	fun `Emit ServiceStarted intent if service is running`() = runTest {

		every { proximityServiceRepository.isServiceRunning() } returns true

		val isServiceRunning = useCase().firstOrNull()

		assertEquals(HomeScreenIntent.ServiceStarted, isServiceRunning)
	}

	@Test
	fun `Emit ServiceStopped intent if service is not running`() = runTest {

		every { proximityServiceRepository.isServiceRunning() } returns false

		val isServiceRunning = useCase().firstOrNull()

		assertEquals(HomeScreenIntent.ServiceStopped, isServiceRunning)
	}
}