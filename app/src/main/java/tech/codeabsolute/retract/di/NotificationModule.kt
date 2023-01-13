package tech.codeabsolute.retract.di

import android.app.NotificationManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import tech.codeabsolute.retract.proximityservice.data.repositories.ProximityNotificationChannelRepository

val notificationModule = module {
	single { androidContext().getSystemService(NotificationManager::class.java) }
	singleOf(::ProximityNotificationChannelRepository)
}