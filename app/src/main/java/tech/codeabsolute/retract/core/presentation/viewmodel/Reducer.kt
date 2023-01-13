package tech.codeabsolute.retract.core.presentation.viewmodel

interface Reducer<STATE, INTENT> {
	fun reduce(state: STATE, intent: INTENT): STATE
}