package tech.codeabsolute.retract.home.presentation.reducer

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class HomeScreenReducerTest {

	private lateinit var reducer: HomeScreenReducer

	@BeforeEach
	fun setUp() {
		reducer = HomeScreenReducer()
	}

	@Test
	fun `StartServiceButtonClicked intent`() {

		val expectedState = HomeScreenState(
			isLoading = true,
			message = "StartServiceButtonClicked"
		)

		val state = reducer.reduce(HomeScreenState(), HomeScreenIntent.StartServiceButtonClicked)

		assertAll(
			{ assertEquals(expectedState.isLoading, state.isLoading) },
			{ assertEquals(expectedState.message, state.message) }
		)
	}

	@Test
	fun `StopServiceButtonClicked intent`() {

		val expectedState = HomeScreenState(
			isLoading = true,
			message = "StopServiceButtonClicked"
		)

		val state = reducer.reduce(HomeScreenState(), HomeScreenIntent.StopServiceButtonClicked)

		assertAll(
			{ assertEquals(expectedState.isLoading, state.isLoading) },
			{ assertEquals(expectedState.message, state.message) }
		)
	}

	@Test
	fun `ServiceStarted intent`() {

		val expectedState = HomeScreenState(
			isLoading = false,
			message = "ServiceStarted"
		)

		val state = reducer.reduce(HomeScreenState(), HomeScreenIntent.ServiceStarted)

		assertAll(
			{ assertEquals(expectedState.isLoading, state.isLoading) },
			{ assertEquals(expectedState.message, state.message) }
		)
	}

	@Test
	fun `ServiceStopped intent`() {

		val expectedState = HomeScreenState(
			isLoading = false,
			message = "ServiceStopped"
		)

		val state = reducer.reduce(HomeScreenState(), HomeScreenIntent.ServiceStopped)

		assertAll(
			{ assertEquals(expectedState.isLoading, state.isLoading) },
			{ assertEquals(expectedState.message, state.message) }
		)
	}

	@Test
	fun `Error intent`() {

		val expectedState = HomeScreenState(
			isLoading = false,
			message = "Error"
		)

		val state = reducer.reduce(HomeScreenState(), HomeScreenIntent.Error)

		assertAll(
			{ assertEquals(expectedState.isLoading, state.isLoading) },
			{ assertEquals(expectedState.message, state.message) }
		)
	}
}