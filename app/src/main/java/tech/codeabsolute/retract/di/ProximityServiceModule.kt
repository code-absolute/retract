package tech.codeabsolute.retract.di

import android.app.ActivityManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import tech.codeabsolute.retract.proximityservice.data.repositories.ProximityServiceRepository
import tech.codeabsolute.retract.proximityservice.data.resourcesproviders.ProximityServiceResourcesProvider
import tech.codeabsolute.retract.proximityservice.domain.usescases.IsProximityServiceRunningUseCase
import tech.codeabsolute.retract.proximityservice.domain.usescases.StartProximityServiceUseCase
import tech.codeabsolute.retract.proximityservice.domain.usescases.StopProximityServiceUseCase
import tech.codeabsolute.retract.proximityservice.presentation.ProximityService

val proximityServiceModule = module {
	factoryOf(::IsProximityServiceRunningUseCase)
	factoryOf(::StartProximityServiceUseCase)
	factoryOf(::StopProximityServiceUseCase)
	single { androidContext().getSystemService(ActivityManager::class.java) }
	singleOf(::ProximityServiceRepository)
	singleOf(::ProximityService)
	singleOf(::ProximityServiceResourcesProvider)
}