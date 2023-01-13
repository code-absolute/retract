package tech.codeabsolute.retract.camera.presentation.reducer

import tech.codeabsolute.retract.core.presentation.viewmodel.Reducer

class CameraScreenReducer : Reducer<CameraScreenState, CameraScreenIntent> {

	override fun reduce(state: CameraScreenState, intent: CameraScreenIntent): CameraScreenState =
		when (intent) {
			is CameraScreenIntent.CheckCameraPermission -> state.copy(
				isLoading = true,
				isCameraPermissionGranted = false,
				showRational = false
			)
			CameraScreenIntent.CameraPermissionGranted -> state.copy(
				isLoading = false,
				isCameraPermissionGranted = true,
				showRational = false
			)
			CameraScreenIntent.CameraPermissionDenied -> state.copy(
				isLoading = false,
				isCameraPermissionGranted = false,
				showRational = false
			)
			CameraScreenIntent.ShowCameraPermissionRational -> state.copy(
				isLoading = false,
				isCameraPermissionGranted = false,
				showRational = true
			)
		}
}