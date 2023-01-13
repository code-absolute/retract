package tech.codeabsolute.retract.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import tech.codeabsolute.retract.camera.presentation.CameraScreenViewModel
import tech.codeabsolute.retract.camera.presentation.reducer.CameraScreenReducer

val cameraModule = module {
	viewModelOf(::CameraScreenViewModel)
	factoryOf(::CameraScreenReducer)
}