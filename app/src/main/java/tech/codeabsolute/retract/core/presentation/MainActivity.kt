package tech.codeabsolute.retract.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import tech.codeabsolute.retract.home.presentation.ui.MainScreen
import tech.codeabsolute.retract.ui.theme.RetractTheme

class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			RetractTheme {
				Scaffold { contentPadding ->
					Surface(
						modifier = Modifier
							.fillMaxSize()
							.padding(contentPadding),
						color = MaterialTheme.colors.background
					) {
						MainScreen()
					}
				}
			}
		}
	}
}
