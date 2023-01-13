package tech.codeabsolute.retract.home.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel
import tech.codeabsolute.retract.camera.presentation.ui.CameraScreen
import tech.codeabsolute.retract.home.presentation.HomeViewModel
import tech.codeabsolute.retract.home.presentation.reducer.HomeScreenIntent
import tech.codeabsolute.retract.home.presentation.reducer.HomeScreenSingleEvent
import tech.codeabsolute.retract.home.presentation.reducer.HomeScreenState
import tech.codeabsolute.retract.ui.theme.RetractTheme

@Composable
fun MainScreen(viewModel: HomeViewModel = koinViewModel()) {

	val uiState by viewModel.viewState.collectAsState()
	val context = LocalContext.current

	var showCamera by remember { mutableStateOf(false) }

	LaunchedEffect(viewModel.singleEvent) {
		viewModel.singleEvent.collect {
			when (it) {
				HomeScreenSingleEvent.SingleEventTriggered -> showCamera = true
			}
		}
	}

	if (showCamera) {
		CameraScreen()
	} else {
		MainScreenContent(
			uiState = uiState,
			onIntent = viewModel::sendIntent,
			onSingleEvent = viewModel::triggerSingleEvent
		)
	}
}

@Composable
fun MainScreenContent(
	uiState: HomeScreenState,
	onIntent: (HomeScreenIntent) -> Unit,
	onSingleEvent: (HomeScreenSingleEvent) -> Unit
) {
	Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		Column {
			Text(text = uiState.message)
			when {
				uiState.isLoading -> CircularProgressIndicator()
				else -> Column {
					if (uiState.isProximityServiceRunning) {
						Button(onClick = { onIntent(HomeScreenIntent.StopServiceButtonClicked) }) {
							Text(text = "Stop service")
						}
					} else {
						Button(onClick = { onIntent(HomeScreenIntent.StartServiceButtonClicked) }) {
							Text(text = "Start service")
						}
					}
					Button(onClick = { onSingleEvent(HomeScreenSingleEvent.SingleEventTriggered) }) {
						Text(text = "Single event")
					}
				}
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun MainScreenContentPreview() {
	RetractTheme {
		MainScreenContent(HomeScreenState(), {}, {})
	}
}