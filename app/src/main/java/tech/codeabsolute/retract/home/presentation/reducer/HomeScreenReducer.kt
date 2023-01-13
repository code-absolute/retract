package tech.codeabsolute.retract.home.presentation.reducer

import tech.codeabsolute.retract.core.presentation.viewmodel.Reducer

class HomeScreenReducer : Reducer<HomeScreenState, HomeScreenIntent> {

	override fun reduce(state: HomeScreenState, intent: HomeScreenIntent): HomeScreenState =
		when (intent) {
			HomeScreenIntent.IsServiceRunning -> state.copy(
				isLoading = true,
				message = "Initializing"
			)
			is HomeScreenIntent.StartServiceButtonClicked -> state.copy(
				isLoading = true,
				message = "StartServiceButtonClicked"
			)
			HomeScreenIntent.StopServiceButtonClicked -> state.copy(
				isLoading = true,
				message = "StopServiceButtonClicked"
			)
			HomeScreenIntent.ServiceStarted -> state.copy(
				isLoading = false,
				isProximityServiceRunning = true,
				message = "ServiceStarted"
			)
			HomeScreenIntent.ServiceStopped -> state.copy(
				isLoading = false,
				isProximityServiceRunning = false,
				message = "ServiceStopped"
			)
			HomeScreenIntent.Error -> state.copy(
				isLoading = false,
				message = "Error"
			)
		}
}