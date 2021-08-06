package dyachenko.kotlinmaterialdesign03.viewmodel

import dyachenko.kotlinmaterialdesign03.model.earth.EarthResponseData
import dyachenko.kotlinmaterialdesign03.model.mars.MarsPhotosResponseData
import dyachenko.kotlinmaterialdesign03.model.pod.PODResponseData

sealed class AppState {
    data class SuccessPOD(val responseData: PODResponseData) : AppState()
    data class SuccessEarth(val responseData: EarthResponseData) : AppState()
    data class SuccessMars(val responseData: MarsPhotosResponseData) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
