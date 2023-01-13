package tech.codeabsolute.retract.home

import androidx.lifecycle.SavedStateHandle
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.codeabsolute.retract.home.presentation.HomeViewModel
import tech.codeabsolute.retract.home.presentation.reducer.HomeScreenIntent
import tech.codeabsolute.retract.home.presentation.reducer.HomeScreenReducer
import tech.codeabsolute.retract.home.presentation.reducer.HomeScreenState
import tech.codeabsolute.retract.proximityservice.domain.usescases.IsProximityServiceRunningUseCase
import tech.codeabsolute.retract.proximityservice.domain.usescases.StartProximityServiceUseCase
import tech.codeabsolute.retract.proximityservice.domain.usescases.StopProximityServiceUseCase

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class HomeViewModelTest {

	@MockK
	private lateinit var savedStateHandle: SavedStateHandle

	@MockK
	private lateinit var reducer: HomeScreenReducer

	@MockK
	private lateinit var isProximityServiceRunningUseCase: IsProximityServiceRunningUseCase

	@MockK
	private lateinit var startProximityServiceUseCase: StartProximityServiceUseCase

	@MockK
	private lateinit var stopProximityServiceUseCase: StopProximityServiceUseCase

	private lateinit var viewModel: HomeViewModel

	private val testDispatcher: TestDispatcher = StandardTestDispatcher()
	private val expectedInitialState = HomeScreenState()

	@BeforeEach
	fun setUp() {
		Dispatchers.setMain(testDispatcher)

		every {
			savedStateHandle.getStateFlow(SAVED_VIEW_STATE_KEY, expectedInitialState)
		} returns MutableStateFlow(expectedInitialState)
		every { isProximityServiceRunningUseCase() } returns flow { emit(HomeScreenIntent.ServiceStarted) }
		every { startProximityServiceUseCase() } returns flow { emit(HomeScreenIntent.ServiceStarted) }
		every { stopProximityServiceUseCase() } returns flow { emit(HomeScreenIntent.ServiceStopped) }

		viewModel = HomeViewModel(
			savedStateHandle,
			reducer,
			isProximityServiceRunningUseCase,
			startProximityServiceUseCase,
			stopProximityServiceUseCase
		)
	}

	@AfterEach
	fun tearDown() {
		Dispatchers.resetMain()
	}

	@Test
	fun `initial view state`() {

		val initialState = viewModel.viewState.value

		assertEquals(expectedInitialState, initialState)
	}

	@Test
	fun `ServiceStarted intent`() = runTest {

		val expectedViewState = mockk<HomeScreenState>()
		val viewIntent = HomeScreenIntent.ServiceStarted
		every {
			reducer.reduce(viewModel.viewState.value, viewIntent)
		} returns expectedViewState

		viewModel.sendIntent(viewIntent)
		advanceUntilIdle()

		assertEquals(expectedViewState, viewModel.viewState.value)
	}

	@Test
	fun `ServiceStopped intent`() = runTest {

		val expectedViewState = mockk<HomeScreenState>()
		val viewIntent = HomeScreenIntent.ServiceStopped
		every {
			reducer.reduce(viewModel.viewState.value, viewIntent)
		} returns expectedViewState

		viewModel.sendIntent(viewIntent)
		advanceUntilIdle()

		assertEquals(expectedViewState, viewModel.viewState.value)
	}

	@Test
	fun `StartServiceButtonClicked intent`() = runTest {

		val initialViewIntent = HomeScreenIntent.StartServiceButtonClicked
		val followUpViewIntent = HomeScreenIntent.ServiceStarted
		val viewStateFromInitialViewIntent = mockk<HomeScreenState>()
		val expectedViewState = mockk<HomeScreenState>()
		every {
			reducer.reduce(viewModel.viewState.value, initialViewIntent)
		} returns viewStateFromInitialViewIntent
		every {
			reducer.reduce(viewStateFromInitialViewIntent, followUpViewIntent)
		} returns expectedViewState

		viewModel.sendIntent(initialViewIntent)
		advanceUntilIdle()

		assertEquals(expectedViewState, viewModel.viewState.value)
	}

	@Test
	fun `StopServiceButtonClicked intent`() = runTest {

		val initialViewIntent = HomeScreenIntent.StopServiceButtonClicked
		val followUpViewIntent = HomeScreenIntent.ServiceStopped
		val viewStateFromInitialViewIntent = mockk<HomeScreenState>()
		val expectedViewState = mockk<HomeScreenState>()
		every {
			reducer.reduce(viewModel.viewState.value, initialViewIntent)
		} returns viewStateFromInitialViewIntent
		every {
			reducer.reduce(viewStateFromInitialViewIntent, followUpViewIntent)
		} returns expectedViewState

		viewModel.sendIntent(initialViewIntent)
		advanceUntilIdle()

		assertEquals(expectedViewState, viewModel.viewState.value)
	}

	companion object {
		private const val SAVED_VIEW_STATE_KEY = "SAVED_VIEW_STATE_KEY"
	}
}