package tech.codeabsolute.retract.core.viewmodel

import androidx.lifecycle.SavedStateHandle
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import tech.codeabsolute.retract.core.presentation.viewmodel.ViewState

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal abstract class StateReducerViewModelTest {

	internal val savedStateHandle: SavedStateHandle = SavedStateHandle()

	private val testDispatcher: TestDispatcher = StandardTestDispatcher()

	internal abstract val initialState: ViewState

	internal abstract fun beforeEach()

	@BeforeEach
	fun setUp() {
		Dispatchers.setMain(testDispatcher)
		beforeEach()
	}

	@AfterEach
	fun tearDown() {
		Dispatchers.resetMain()
	}

	companion object {
		internal const val SAVED_VIEW_STATE_KEY = "SAVED_VIEW_STATE_KEY"
	}
}