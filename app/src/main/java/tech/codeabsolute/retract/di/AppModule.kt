package tech.codeabsolute.retract.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import tech.codeabsolute.retract.home.presentation.HomeViewModel
import tech.codeabsolute.retract.home.presentation.reducer.HomeScreenReducer

val appModule = module {
	includes(
		cameraModule,
		notificationModule,
		proximityServiceModule
	)
	viewModelOf(::HomeViewModel)
	factoryOf(::HomeScreenReducer)
}