package tech.codeabsolute.retract.camera.presentation.reducer

import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import tech.codeabsolute.retract.core.presentation.viewmodel.ViewState

@Immutable
@Parcelize
data class CameraScreenState(
	val isLoading: Boolean = false,
	val isCameraPermissionGranted: Boolean = false,
	val showRational: Boolean = false
) : ViewState
