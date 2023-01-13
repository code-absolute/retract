package tech.codeabsolute.retract.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class AppModuleCheck : KoinTest {

	@JvmField
	@RegisterExtension
	val koinTestExtension = KoinTestExtension.create {
		printLogger(Level.ERROR)
		modules(notificationModule)
	}

	@Test
	fun `Verify Koin Module`() {

		appModule.verify(
			extraTypes = listOf(
				CharSequence::class,
				Context::class,
				Int::class,
				SavedStateHandle::class,
				String::class
			)
		)
	}
}