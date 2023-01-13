package tech.codeabsolute.retract.home.presentation.reducer

import tech.codeabsolute.retract.core.presentation.viewmodel.ViewSingleEvent

sealed interface HomeScreenSingleEvent : ViewSingleEvent {
	object SingleEventTriggered : HomeScreenSingleEvent
}