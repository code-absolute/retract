package tech.codeabsolute.retract.proximityservice.data.resourcesproviders

import android.content.Context
import tech.codeabsolute.retract.R
import tech.codeabsolute.retract.core.domain.resourcesproviders.ResourcesProvider

class ProximityServiceResourcesProvider(
	private val context: Context
) : ResourcesProvider {

	val notificationChannelName: String
		get() = context.getString(R.string.proximity_service_notification_channel_name)

	val notificationChannelDescription: String
		get() = context.getString(R.string.proximity_service_notification_channel_description)

	val notificationTitle: String
		get() = context.getString(R.string.proximity_service_notification_title)

	val notificationText: String
		get() = context.getString(R.string.proximity_service_notification_text)

	val notificationIconResId: Int
		get() = R.drawable.ic_launcher_foreground
}