package tech.codeabsolute.retract.camera.presentation.ui

import android.Manifest
import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel
import tech.codeabsolute.retract.camera.presentation.CameraScreenViewModel
import tech.codeabsolute.retract.camera.presentation.reducer.CameraScreenIntent
import tech.codeabsolute.retract.camera.presentation.reducer.CameraScreenSingleEvent
import tech.codeabsolute.retract.camera.presentation.reducer.CameraScreenState
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(viewModel: CameraScreenViewModel = koinViewModel()) {

	val state by viewModel.viewState.collectAsState()
	val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

	LaunchedEffect(cameraPermissionState.status) {
		viewModel.sendIntent(CameraScreenIntent.CheckCameraPermission(cameraPermissionState))
	}

	LaunchedEffect(viewModel.singleEvent) {
		viewModel.singleEvent.collect {
			when (it) {
				CameraScreenSingleEvent.LaunchPermissionRequest ->
					cameraPermissionState.launchPermissionRequest()
			}
		}
	}

	CameraScreenContent(state, viewModel::sendIntent, viewModel::triggerSingleEvent)
}

@Composable
fun CameraScreenContent(
	state: CameraScreenState,
	onIntent: (CameraScreenIntent) -> Unit,
	onSingleEvent: (CameraScreenSingleEvent) -> Unit
) {
	if (state.isCameraPermissionGranted) {
		PermissionGrantedView()
	} else {
		Column {
			val textToShow = if (state.showRational) {
				"The camera is important for this app. Please grant the permission."
			} else {
				"Camera permission required for this feature to be available. Please grant the permission"
			}
			Text(textToShow)
			Button(onClick = { onSingleEvent(CameraScreenSingleEvent.LaunchPermissionRequest) }) {
				Text("Request permission")
			}
		}
	}
}

@Composable
fun PermissionGrantedView() {
	val context = LocalContext.current
	val lifecycleOwner = LocalLifecycleOwner.current

	val previewView = remember { PreviewView(context) }
	val videoCapture: MutableState<VideoCapture<Recorder>?> = remember { mutableStateOf(null) }

	LaunchedEffect(previewView) {
		videoCapture.value = context.createVideoCaptureUseCase(
			lifecycleOwner = lifecycleOwner,
			cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA,
			previewView = previewView
		)
	}

	AndroidView(
		factory = { previewView },
		modifier = Modifier.fillMaxSize()
	)
}

suspend fun Context.createVideoCaptureUseCase(
	lifecycleOwner: LifecycleOwner,
	cameraSelector: CameraSelector,
	previewView: PreviewView
): VideoCapture<Recorder> {
	val preview = Preview.Builder()
		.build()
		.apply { setSurfaceProvider(previewView.surfaceProvider) }

	val qualitySelector = QualitySelector.from(
		Quality.FHD,
		FallbackStrategy.lowerQualityOrHigherThan(Quality.FHD)
	)
	val recorder = Recorder.Builder()
		.setExecutor(mainExecutor)
		.setQualitySelector(qualitySelector)
		.build()
	val videoCapture = VideoCapture.withOutput(recorder)

	val cameraProvider = getCameraProvider()
	cameraProvider.unbindAll()
	cameraProvider.bindToLifecycle(
		lifecycleOwner,
		cameraSelector,
		preview,
		videoCapture
	)

	return videoCapture
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
	ProcessCameraProvider.getInstance(this).also { future ->
		future.addListener({
			continuation.resume(future.get())
		}, ContextCompat.getMainExecutor(this))
	}
}