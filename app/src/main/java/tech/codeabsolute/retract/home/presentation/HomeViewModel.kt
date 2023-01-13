package tech.codeabsolute.retract.home.presentation

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.Flow
import tech.codeabsolute.retract.core.presentation.viewmodel.StateReducerViewModel
import tech.codeabsolute.retract.home.presentation.reducer.HomeScreenIntent
import tech.codeabsolute.retract.home.presentation.reducer.HomeScreenReducer
import tech.codeabsolute.retract.home.presentation.reducer.HomeScreenSingleEvent
import tech.codeabsolute.retract.home.presentation.reducer.HomeScreenState
import tech.codeabsolute.retract.proximityservice.domain.usescases.IsProximityServiceRunningUseCase
import tech.codeabsolute.retract.proximityservice.domain.usescases.StartProximityServiceUseCase
import tech.codeabsolute.retract.proximityservice.domain.usescases.StopProximityServiceUseCase

class HomeViewModel(
	override val savedStateHandle: SavedStateHandle,
	override val reducer: HomeScreenReducer,
	private val isProximityServiceRunningUseCase: IsProximityServiceRunningUseCase,
	private val startProximityServiceUseCase: StartProximityServiceUseCase,
	private val stopProximityServiceUseCase: StopProximityServiceUseCase
) : StateReducerViewModel<HomeScreenState, HomeScreenIntent, HomeScreenSingleEvent>() {

	override val initialState: HomeScreenState
		get() = HomeScreenState()

	init {
		sendIntent(HomeScreenIntent.IsServiceRunning)
	}

	override fun handleIntent(
		intent: HomeScreenIntent,
		state: HomeScreenState
	): Flow<HomeScreenIntent>? = when (intent) {
		HomeScreenIntent.IsServiceRunning -> isProximityServiceRunningUseCase()
		HomeScreenIntent.StartServiceButtonClicked -> startProximityServiceUseCase()
		HomeScreenIntent.StopServiceButtonClicked -> stopProximityServiceUseCase()
		HomeScreenIntent.ServiceStarted -> null
		HomeScreenIntent.ServiceStopped -> null
		HomeScreenIntent.Error -> null
	}
}
