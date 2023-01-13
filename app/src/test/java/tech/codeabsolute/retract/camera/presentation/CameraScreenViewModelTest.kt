package tech.codeabsolute.retract.camera.presentation

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.codeabsolute.retract.camera.domain.usecases.CheckCameraPermissionUseCase
import tech.codeabsolute.retract.camera.presentation.reducer.CameraScreenIntent
import tech.codeabsolute.retract.camera.presentation.reducer.CameraScreenReducer
import tech.codeabsolute.retract.camera.presentation.reducer.CameraScreenState
import tech.codeabsolute.retract.core.viewmodel.StateReducerViewModelTest

@OptIn(
	ExperimentalCoroutinesApi::class,
	ExperimentalPermissionsApi::class
)
@ExtendWith(MockKExtension::class)
internal class CameraScreenViewModelTest : StateReducerViewModelTest() {

	override val initialState = CameraScreenState()

	@MockK
	private lateinit var reducer: CameraScreenReducer

	@MockK
	private lateinit var checkCameraPermissionUseCase: CheckCameraPermissionUseCase

	private lateinit var viewModel: CameraScreenViewModel

	override fun beforeEach() {

		viewModel = CameraScreenViewModel(
			savedStateHandle,
			reducer,
			checkCameraPermissionUseCase
		)
	}

	@Test
	fun `Set initial state`() {

		val state = viewModel.viewState.value

		assertEquals(CameraScreenState(), state)
	}

	@Test
	fun `Handle intent CameraPermissionGranted`() = runTest {

		val handledIntent = CameraScreenIntent.CameraPermissionGranted
		val reducedState = mockk<CameraScreenState>()
		every { reducer.reduce(initialState, handledIntent) } returns reducedState

		viewModel.sendIntent(handledIntent)
		advanceUntilIdle()

		assertEquals(reducedState, savedStateHandle[SAVED_VIEW_STATE_KEY])
	}

	@Test
	fun `Handle intent CameraPermissionDenied`() = runTest {

		val handledIntent = CameraScreenIntent.CameraPermissionDenied
		val reducedState = mockk<CameraScreenState>()
		every { reducer.reduce(initialState, handledIntent) } returns reducedState

		viewModel.sendIntent(handledIntent)
		advanceUntilIdle()

		assertEquals(reducedState, savedStateHandle[SAVED_VIEW_STATE_KEY])
	}

	@Test
	fun `Handle intent ShowCameraPermissionRational`() = runTest {

		val handledIntent = CameraScreenIntent.ShowCameraPermissionRational
		val reducedState = mockk<CameraScreenState>()
		every { reducer.reduce(initialState, handledIntent) } returns reducedState

		viewModel.sendIntent(handledIntent)
		advanceUntilIdle()

		assertEquals(reducedState, savedStateHandle[SAVED_VIEW_STATE_KEY])
	}

	@Test
	fun `Handle intent CheckCameraPermission`() = runTest {

		val handledIntent = CameraScreenIntent.CheckCameraPermission(mockk())
		val reducedState = mockk<CameraScreenState>()
		val finalState = mockk<CameraScreenState>()
		every { reducer.reduce(initialState, handledIntent) } returns reducedState
		every {
			reducer.reduce(
				reducedState,
				CameraScreenIntent.CameraPermissionGranted
			)
		} returns finalState
		every {
			checkCameraPermissionUseCase(handledIntent.cameraPermissionState)
		} returns flow {
			emit(CameraScreenIntent.CameraPermissionGranted)
		}

		viewModel.sendIntent(handledIntent)
		advanceUntilIdle()

		verify { checkCameraPermissionUseCase(handledIntent.cameraPermissionState) }
		assertEquals(finalState, savedStateHandle[SAVED_VIEW_STATE_KEY])
	}
}