package tech.codeabsolute.retract.core.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class StateReducerViewModel
<VIEW_STATE : ViewState, VIEW_INTENT : ViewIntent, VIEW_SINGLE_EVENT : ViewSingleEvent>
	: ViewModel() {

	protected abstract val initialState: VIEW_STATE
	protected abstract val reducer: Reducer<VIEW_STATE, VIEW_INTENT>
	protected abstract val savedStateHandle: SavedStateHandle

	private val intentFlow: MutableSharedFlow<VIEW_INTENT> = MutableSharedFlow()

	private val _singleEvent: MutableSharedFlow<VIEW_SINGLE_EVENT> = MutableSharedFlow()
	val singleEvent: Flow<VIEW_SINGLE_EVENT> = _singleEvent

	val viewState by lazy { savedStateHandle.getStateFlow(SAVED_VIEW_STATE_KEY, initialState) }

	init {
		subscribeToIntents()
	}

	private fun subscribeToIntents() {
		viewModelScope.launch {
			intentFlow.collect { intent ->
				reducer.reduce(viewState.value, intent).let { reducedState ->
					savedStateHandle[SAVED_VIEW_STATE_KEY] = reducedState
					handleIntent(intent, reducedState)?.collect(::sendIntent)
				}
			}
		}
	}

	protected abstract fun handleIntent(
		intent: VIEW_INTENT,
		state: VIEW_STATE
	): Flow<VIEW_INTENT>?

	fun sendIntent(intent: VIEW_INTENT) {
		viewModelScope.launch {
			intentFlow.emit(intent)
		}
	}

	fun triggerSingleEvent(singleEvent: VIEW_SINGLE_EVENT) {
		viewModelScope.launch {
			_singleEvent.emit(singleEvent)
		}
	}

	companion object {
		private const val SAVED_VIEW_STATE_KEY = "SAVED_VIEW_STATE_KEY"
	}
}