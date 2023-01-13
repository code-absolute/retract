package tech.codeabsolute.retract.camera.presentation.reducer

import tech.codeabsolute.retract.core.presentation.viewmodel.ViewSingleEvent

interface CameraScreenSingleEvent : ViewSingleEvent {
	object LaunchPermissionRequest : CameraScreenSingleEvent
}
