package tech.codeabsolute.retract.proximityservice.data.resourcesproviders

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.junit4.MockKRule
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import tech.codeabsolute.retract.R

@RunWith(AndroidJUnit4::class)
internal class ProximityServiceStringsTest {

	@get:Rule
	val mockkRule = MockKRule(this)

	private val context = ApplicationProvider.getApplicationContext<Context>()
	private lateinit var resourcesProvider: ProximityServiceResourcesProvider

	@Before
	fun setUp() {

		resourcesProvider = ProximityServiceResourcesProvider(context)
	}

	@After
	fun tearDown() {
		stopKoin()
	}

	@Test
	fun `Proximity service notification channel name`() {

		assertEquals(
			"Proximity Service",
			context.getString(R.string.proximity_service_notification_channel_name)
		)
	}

	@Test
	fun `Proximity service notification channel description`() {

		assertEquals(
			"Proximity Service description.",
			context.getString(R.string.proximity_service_notification_channel_description)
		)
	}

	@Test
	fun `Proximity service notification notification title`() {

		assertEquals(
			"Proximity Service",
			context.getString(R.string.proximity_service_notification_title)
		)
	}

	@Test
	fun `Proximity service notification notification text`() {

		assertEquals(
			"Proximity service is running.",
			context.getString(R.string.proximity_service_notification_text)
		)
	}
}