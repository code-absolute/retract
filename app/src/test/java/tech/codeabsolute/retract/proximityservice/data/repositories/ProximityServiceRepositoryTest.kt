package tech.codeabsolute.retract.proximityservice.data.repositories

import android.app.ActivityManager
import android.app.ActivityManager.RunningServiceInfo
import android.content.Context
import android.content.Intent
import io.mockk.EqMatcher
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.codeabsolute.retract.proximityservice.domain.models.ProximityServiceAction
import tech.codeabsolute.retract.proximityservice.presentation.ProximityService
import tech.codeabsolute.retract.random

@ExtendWith(MockKExtension::class)
internal class ProximityServiceRepositoryTest {

	@MockK
	private lateinit var context: Context

	@MockK
	private lateinit var activityManager: ActivityManager

	private lateinit var proximityServiceRepository: ProximityServiceRepository

	@BeforeEach
	fun setUp() {
		proximityServiceRepository = ProximityServiceRepository(context, activityManager)
	}

	@Test
	fun `Create Intent for ProximityService with action START_SERVICE_INTENT_ACTION and then start a foreground service`() {

		val intent = mockk<Intent>()
		mockkConstructor(Intent::class)
		every {
			constructedWith<Intent>(
				EqMatcher(context),
				EqMatcher(ProximityService::class.java)
			).setAction(ProximityServiceAction.START_SERVICE_INTENT_ACTION.name)
		} returns intent
		every { context.startForegroundService(intent) } returns mockk()

		proximityServiceRepository.startService()

		verify { context.startForegroundService(intent) }
	}

	@Test
	fun `Create Intent for ProximityService with action STOP_SERVICE_INTENT_ACTION and then stop the service`() {

		val intent = mockk<Intent>()
		mockkConstructor(Intent::class)
		every {
			constructedWith<Intent>(
				EqMatcher(context),
				EqMatcher(ProximityService::class.java)
			).setAction(ProximityServiceAction.STOP_SERVICE_INTENT_ACTION.name)
		} returns intent
		every { context.stopService(intent) } returns Boolean.random()

		proximityServiceRepository.stopService()

		verify { context.stopService(intent) }
	}

	@Test
	fun `Check if service is running and return false if it is not`() {

		every { activityManager.getRunningServices(Int.MAX_VALUE) } returns listOf()

		val isServiceRunning = proximityServiceRepository.isServiceRunning()

		assertFalse(isServiceRunning)
	}

	@Test
	fun `Check if service is running and return true if it is`() {

		val runningServiceInfo = mockk<RunningServiceInfo> {
			service = mockk {
				every { className } returns ProximityService::class.java.name
			}
		}
		every {
			activityManager.getRunningServices(Int.MAX_VALUE)
		} returns listOf(runningServiceInfo)

		val isServiceRunning = proximityServiceRepository.isServiceRunning()

		assertTrue(isServiceRunning)
	}
}