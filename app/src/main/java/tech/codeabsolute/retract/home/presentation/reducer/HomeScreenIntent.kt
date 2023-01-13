package tech.codeabsolute.retract.home.presentation.reducer

import tech.codeabsolute.retract.core.presentation.viewmodel.ViewIntent

sealed interface HomeScreenIntent : ViewIntent {
	object IsServiceRunning : HomeScreenIntent
	object StartServiceButtonClicked : HomeScreenIntent
	object StopServiceButtonClicked : HomeScreenIntent
	object ServiceStarted : HomeScreenIntent
	object ServiceStopped : HomeScreenIntent
	object Error : HomeScreenIntent
}