package dyachenko.kotlinmaterialdesign03.viewmodel

sealed class AppState {
    data class Success<T>(val responseData: T) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
