package tech.codeabsolute.retract.proximityservice.presentation

import android.app.Notification
import android.app.Service
import android.content.Intent
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock
import tech.codeabsolute.retract.di.appModule
import tech.codeabsolute.retract.proximityservice.data.repositories.ProximityNotificationChannelRepository
import tech.codeabsolute.retract.proximityservice.domain.models.ProximityServiceAction
import tech.codeabsolute.retract.random

internal class ProximityServiceTest : KoinTest {

	@JvmField
	@RegisterExtension
	val koinTestExtension = KoinTestExtension.create {
		printLogger(Level.ERROR)
		modules(appModule)
	}

	@JvmField
	@RegisterExtension
	val mockProvider = MockProviderExtension.create { clazz ->
		mockkClass(clazz)
	}

	private lateinit var proximityService: ProximityService

	@Test
	fun `onBind return null`() {

		proximityService = ProximityService()

		val binder = proximityService.onBind(mockk())

		assertNull(binder)
	}

	@Test
	fun `Build notification from ProximityServiceNotificationManager if ProximityServiceAction is START_SERVICE_INTENT_ACTION`() {

		proximityService = ProximityService()
		val proximityNotificationChannelRepository =
			declareMock<ProximityNotificationChannelRepository> {
				every { buildNotification(proximityService) } returns mockk()
			}

		startService()

		verify { proximityNotificationChannelRepository.buildNotification(proximityService) }
	}

	private fun startService() {

		val intent = mockk<Intent> {
			every { action } returns ProximityServiceAction.START_SERVICE_INTENT_ACTION.name
		}

		proximityService.onStartCommand(intent, Int.random(), Int.random())
	}

	@Test
	fun `Start service in foreground if ProximityServiceAction is START_SERVICE_INTENT_ACTION`() {

		proximityService = spyk(ProximityService())
		val notification = mockk<Notification>()
		declareMock<ProximityNotificationChannelRepository> {
			every { buildNotification(proximityService) } returns notification
		}

		startService()

		verify { proximityService.startForeground(NOTIFICATION_ID, notification) }
	}

	@Test
	fun `Stop foreground service if ProximityServiceAction is STOP_SERVICE_INTENT_ACTION`() {

		proximityService = spyk(ProximityService())

		stopService()

		verify { proximityService.stopForeground(Service.STOP_FOREGROUND_REMOVE) }
	}

	private fun stopService(startId: Int = Int.random()) {

		val intent = mockk<Intent> {
			every { action } returns ProximityServiceAction.STOP_SERVICE_INTENT_ACTION.name
		}

		proximityService.onStartCommand(intent, Int.random(), startId)
	}

	@Test
	fun `Stop self`() {

		proximityService = spyk(ProximityService())
		val startId: Int = Int.random()

		stopService(startId)

		verify { proximityService.stopSelf(startId) }
	}

	companion object {
		private const val NOTIFICATION_ID = 1001
	}
}