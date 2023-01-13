package tech.codeabsolute.retract.camera.domain.usecases

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tech.codeabsolute.retract.camera.presentation.reducer.CameraScreenIntent

@OptIn(
	ExperimentalCoroutinesApi::class,
	ExperimentalPermissionsApi::class
)
internal class CheckCameraPermissionUseCaseTest {

	private lateinit var useCase: CheckCameraPermissionUseCase

	@BeforeEach
	fun setUp() {

		useCase = CheckCameraPermissionUseCase()
	}

	@Test
	fun `If camera permission is granted emit intent CameraPermissionGranted`() = runTest {

		val permissionState = mockk<PermissionState> {
			every { status } returns PermissionStatus.Granted
		}

		val intent = useCase(permissionState).firstOrNull()

		assertEquals(CameraScreenIntent.CameraPermissionGranted, intent)
	}

	@Test
	fun `If camera permission is denied emit intent CameraPermissionDenied`() = runTest {

		val permissionState = mockk<PermissionState> {
			every { status } returns PermissionStatus.Denied(shouldShowRationale = false)
		}

		val intent = useCase(permissionState).firstOrNull()

		assertEquals(CameraScreenIntent.CameraPermissionDenied, intent)
	}

	@Test
	fun `If camera permission should show rational emit intent ShowCameraPermissionRational`() =
		runTest {

			val permissionState = mockk<PermissionState> {
				every { status } returns PermissionStatus.Denied(shouldShowRationale = true)
			}

			val intent = useCase(permissionState).firstOrNull()

			assertEquals(CameraScreenIntent.ShowCameraPermissionRational, intent)
		}
}