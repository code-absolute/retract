package tech.codeabsolute.retract.proximityservice.data.resourcesproviders

import android.content.Context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.codeabsolute.retract.R
import tech.codeabsolute.retract.random

@ExtendWith(MockKExtension::class)
internal class ProximityServiceResourcesProviderTest {

	@MockK
	private lateinit var context: Context

	private lateinit var resourcesProvider: ProximityServiceResourcesProvider

	@BeforeEach
	fun setUp() {

		resourcesProvider = ProximityServiceResourcesProvider(context)
	}

	@Test
	fun `Get notificationChannelName from string resource proximity_service_notification_channel_name`() {

		val expectedString = String.random()
		every { context.getString(R.string.proximity_service_notification_channel_name) } returns expectedString

		assertEquals(expectedString, resourcesProvider.notificationChannelName)
	}


	@Test
	fun `Get notificationChannelDescription from string resource proximity_service_notification_channel_description`() {

		val expectedString = String.random()
		every { context.getString(R.string.proximity_service_notification_channel_description) } returns expectedString

		assertEquals(expectedString, resourcesProvider.notificationChannelDescription)
	}

	@Test
	fun `Get notificationTitle from string resource proximity_service_notification_title`() {

		val expectedString = String.random()
		every { context.getString(R.string.proximity_service_notification_title) } returns expectedString

		assertEquals(expectedString, resourcesProvider.notificationTitle)
	}

	@Test
	fun `Get notificationText from string resource proximity_service_notification_text`() {

		val expectedString = String.random()
		every { context.getString(R.string.proximity_service_notification_text) } returns expectedString

		assertEquals(expectedString, resourcesProvider.notificationText)
	}

	@Test
	fun `Get notificationIconResId from string resource ic_launcher_foreground`() {

		assertEquals(
			R.drawable.ic_launcher_foreground,
			resourcesProvider.notificationIconResId
		)
	}
}