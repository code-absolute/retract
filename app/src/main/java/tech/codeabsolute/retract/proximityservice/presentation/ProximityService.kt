package tech.codeabsolute.retract.proximityservice.presentation

import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.koin.android.ext.android.inject
import tech.codeabsolute.retract.proximityservice.data.repositories.ProximityNotificationChannelRepository
import tech.codeabsolute.retract.proximityservice.domain.models.ProximityServiceAction

class ProximityService : Service() {

	private val proximityNotificationChannelRepository: ProximityNotificationChannelRepository by inject()

	override fun onBind(intent: Intent): IBinder? = null

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

		when (intent.action) {
			ProximityServiceAction.START_SERVICE_INTENT_ACTION.name -> start()
			ProximityServiceAction.STOP_SERVICE_INTENT_ACTION.name -> stop(startId)
		}

		return super.onStartCommand(intent, flags, startId)
	}

	private fun start() {

		val notification = proximityNotificationChannelRepository.buildNotification(this)

		startForeground(
			NOTIFICATION_ID,
			notification
		)
	}

	private fun stop(startId: Int) {
		stopForeground(STOP_FOREGROUND_REMOVE)
		stopSelf(startId)
	}

	companion object {
		private const val NOTIFICATION_ID = 1001
	}
}