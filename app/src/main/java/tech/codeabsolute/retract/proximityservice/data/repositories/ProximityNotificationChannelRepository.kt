package tech.codeabsolute.retract.proximityservice.data.repositories

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import tech.codeabsolute.retract.proximityservice.data.resourcesproviders.ProximityServiceResourcesProvider

class ProximityNotificationChannelRepository(
	notificationManager: NotificationManager,
	private val resourcesProvider: ProximityServiceResourcesProvider
) {

	private val notificationChannel = NotificationChannel(
		NOTIFICATION_CHANNEL_ID,
		resourcesProvider.notificationChannelName,
		NOTIFICATION_CHANNEL_IMPORTANCE
	)

	init {
		notificationManager.createNotificationChannel(notificationChannel)
	}

	fun buildNotification(serviceContext: Context): Notification =
		Notification.Builder(serviceContext, notificationChannel.id)
			.setContentTitle(resourcesProvider.notificationTitle)
			.setContentText(resourcesProvider.notificationText)
			.setSmallIcon(resourcesProvider.notificationIconResId)
			.build()

	companion object {
		private const val NOTIFICATION_CHANNEL_ID = "proximity_service"
		private const val NOTIFICATION_CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT
	}
}