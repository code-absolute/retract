package tech.codeabsolute.retract.proximityservice.data.repositories

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.assertAll
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import tech.codeabsolute.retract.proximityservice.data.resourcesproviders.ProximityServiceResourcesProvider
import tech.codeabsolute.retract.random

@RunWith(AndroidJUnit4::class)
class ProximityNotificationChannelRepositoryTest {

	@get:Rule
	val mockkRule = MockKRule(this)

	@MockK
	private lateinit var resourcesProvider: ProximityServiceResourcesProvider

	private lateinit var context: Context
	private lateinit var notificationManager: NotificationManager
	private lateinit var proximityNotificationChannelRepository: ProximityNotificationChannelRepository

	@Before
	fun setUp() {

		context = ApplicationProvider.getApplicationContext()
		notificationManager = context.getSystemService(NotificationManager::class.java)
		mockResourcesProvider()

		proximityNotificationChannelRepository =
			ProximityNotificationChannelRepository(
				notificationManager,
				resourcesProvider
			)
	}

	private fun mockResourcesProvider() {
		every { resourcesProvider.notificationChannelName } returns String.random()
		every { resourcesProvider.notificationTitle } returns String.random()
		every { resourcesProvider.notificationText } returns String.random()
		every { resourcesProvider.notificationIconResId } returns Int.random()
	}

	@After
	fun tearDown() {

		val notificationChannel = notificationManager.notificationChannels.firstOrNull()
		notificationManager.deleteNotificationChannel(notificationChannel?.id)

		stopKoin()
	}

	@Test
	fun `On init create notification channel from proximity service`() {

		val notificationChannel = notificationManager.notificationChannels.firstOrNull()

		assertAll(
			{ assertEquals(NOTIFICATION_CHANNEL_ID, notificationChannel?.id) },
			{ assertEquals(resourcesProvider.notificationChannelName, notificationChannel?.name) },
			{ assertEquals(NOTIFICATION_CHANNEL_IMPORTANCE, notificationChannel?.importance) }
		)
	}

	@Test
	fun `Build notification for proximity service`() {

		val notification = proximityNotificationChannelRepository.buildNotification(context)

		assertAll(
			{ assertEquals(NOTIFICATION_CHANNEL_ID, notification.channelId) },
			{
				assertEquals(
					resourcesProvider.notificationTitle,
					notification.extras.getString(Notification.EXTRA_TITLE)
				)
			},
			{
				assertEquals(
					resourcesProvider.notificationText,
					notification.extras.getString(Notification.EXTRA_TEXT)
				)
			},
			{ assertEquals(resourcesProvider.notificationIconResId, notification.smallIcon.resId) }
		)
	}

	companion object {
		private const val NOTIFICATION_CHANNEL_ID = "proximity_service"
		private const val NOTIFICATION_CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT
	}
}