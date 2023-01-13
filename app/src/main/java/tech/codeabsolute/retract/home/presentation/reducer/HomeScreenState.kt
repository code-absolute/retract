package tech.codeabsolute.retract.home.presentation.reducer

import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import tech.codeabsolute.retract.core.domain.util.empty
import tech.codeabsolute.retract.core.presentation.viewmodel.ViewState

@Immutable
@Parcelize
data class HomeScreenState(
	val isLoading: Boolean = false,
	val isProximityServiceRunning: Boolean = false,
	val message: String = String.empty()
) : ViewState
