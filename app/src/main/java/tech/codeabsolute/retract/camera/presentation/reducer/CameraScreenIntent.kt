package tech.codeabsolute.retract.camera.presentation.reducer

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import tech.codeabsolute.retract.core.presentation.viewmodel.ViewIntent

@OptIn(ExperimentalPermissionsApi::class)
sealed interface CameraScreenIntent : ViewIntent {
	data class CheckCameraPermission(
		val cameraPermissionState: PermissionState
	) : CameraScreenIntent

	object CameraPermissionGranted : CameraScreenIntent
	object CameraPermissionDenied : CameraScreenIntent
	object ShowCameraPermissionRational : CameraScreenIntent
}