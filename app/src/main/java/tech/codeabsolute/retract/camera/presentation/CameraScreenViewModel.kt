package tech.codeabsolute.retract.camera.presentation

import androidx.lifecycle.SavedStateHandle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.flow.Flow
import tech.codeabsolute.retract.camera.domain.usecases.CheckCameraPermissionUseCase
import tech.codeabsolute.retract.camera.presentation.reducer.CameraScreenIntent
import tech.codeabsolute.retract.camera.presentation.reducer.CameraScreenReducer
import tech.codeabsolute.retract.camera.presentation.reducer.CameraScreenSingleEvent
import tech.codeabsolute.retract.camera.presentation.reducer.CameraScreenState
import tech.codeabsolute.retract.core.presentation.viewmodel.StateReducerViewModel

@OptIn(ExperimentalPermissionsApi::class)
class CameraScreenViewModel(
	override val savedStateHandle: SavedStateHandle,
	override val reducer: CameraScreenReducer,
	private val checkCameraPermissionUseCase: CheckCameraPermissionUseCase
) : StateReducerViewModel<CameraScreenState, CameraScreenIntent, CameraScreenSingleEvent>() {

	override val initialState: CameraScreenState
		get() = CameraScreenState()

	override fun handleIntent(
		intent: CameraScreenIntent,
		state: CameraScreenState
	): Flow<CameraScreenIntent>? = when (intent) {
		is CameraScreenIntent.CheckCameraPermission -> checkCameraPermissionUseCase(intent.cameraPermissionState)
		CameraScreenIntent.CameraPermissionGranted -> null
		CameraScreenIntent.CameraPermissionDenied -> null
		CameraScreenIntent.ShowCameraPermissionRational -> null
	}
}