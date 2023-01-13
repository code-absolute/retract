package tech.codeabsolute.retract.proximityservice.domain.usescases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.codeabsolute.retract.core.domain.usecases.UseCaseWithNoInput
import tech.codeabsolute.retract.home.presentation.reducer.HomeScreenIntent
import tech.codeabsolute.retract.proximityservice.data.repositories.ProximityServiceRepository

class StartProximityServiceUseCase(
	private val proximityServiceRepository: ProximityServiceRepository
) : UseCaseWithNoInput<HomeScreenIntent> {

	override fun invoke(): Flow<HomeScreenIntent> = flow {
		proximityServiceRepository.startService()?.let {
			emit(HomeScreenIntent.ServiceStarted)
		} ?: emit(HomeScreenIntent.Error)
	}
}