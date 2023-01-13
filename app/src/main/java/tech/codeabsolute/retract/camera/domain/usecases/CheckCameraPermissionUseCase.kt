package tech.codeabsolute.retract.camera.domain.usecases

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.codeabsolute.retract.camera.presentation.reducer.CameraScreenIntent
import tech.codeabsolute.retract.core.domain.usecases.UseCase

@OptIn(ExperimentalPermissionsApi::class)
class CheckCameraPermissionUseCase : UseCase<PermissionState, CameraScreenIntent> {

	override fun invoke(input: PermissionState): Flow<CameraScreenIntent> = flow {
		when (input.status) {
			PermissionStatus.Granted -> emit(CameraScreenIntent.CameraPermissionGranted)
			is PermissionStatus.Denied -> if (input.status.shouldShowRationale) {
				emit(CameraScreenIntent.ShowCameraPermissionRational)
			} else {
				emit(CameraScreenIntent.CameraPermissionDenied)
			}
		}
	}
}