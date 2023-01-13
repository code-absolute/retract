package tech.codeabsolute.retract.proximityservice.data.repositories

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import tech.codeabsolute.retract.proximityservice.domain.models.ProximityServiceAction
import tech.codeabsolute.retract.proximityservice.presentation.ProximityService

class ProximityServiceRepository(
	private val context: Context,
	private val activityManager: ActivityManager
) {

	fun startService(): ComponentName? {
		val intent = Intent(context, ProximityService::class.java)
			.setAction(ProximityServiceAction.START_SERVICE_INTENT_ACTION.name)
		return context.startForegroundService(intent)
	}

	fun stopService(): Boolean {
		val intent = Intent(context, ProximityService::class.java)
			.setAction(ProximityServiceAction.STOP_SERVICE_INTENT_ACTION.name)
		return context.stopService(intent)
	}

	fun isServiceRunning(): Boolean {
		return activityManager.getRunningServices(Int.MAX_VALUE).any {
			ProximityService::class.java.name == it.service.className
		}
	}
}