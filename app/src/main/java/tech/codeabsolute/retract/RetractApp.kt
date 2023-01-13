package tech.codeabsolute.retract

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import tech.codeabsolute.retract.di.appModule

class RetractApp : Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidLogger()
			androidContext(this@RetractApp)
			modules(appModule)
		}
	}
}