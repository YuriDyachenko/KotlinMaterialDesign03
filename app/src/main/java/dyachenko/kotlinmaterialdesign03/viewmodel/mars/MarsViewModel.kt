package dyachenko.kotlinmaterialdesign03.viewmodel.mars

import dyachenko.kotlinmaterialdesign03.BuildConfig
import dyachenko.kotlinmaterialdesign03.R
import dyachenko.kotlinmaterialdesign03.model.mars.MarsPhotosResponseData
import dyachenko.kotlinmaterialdesign03.model.settings.SettingsData
import dyachenko.kotlinmaterialdesign03.viewmodel.AppState
import dyachenko.kotlinmaterialdesign03.viewmodel.CommonViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel : CommonViewModel() {

    fun getMarsPhotosFromServer(dateString: String) {
        data.value = AppState.Loading
        val apiKey = BuildConfig.NASA_API_KEY
        val camera = SettingsData.marsRoverCamera
        retrofitImpl.getMarsRetrofitImpl().getMarsPhotos(camera, apiKey, dateString)
            .enqueue(object :
                Callback<MarsPhotosResponseData> {
                override fun onResponse(
                    call: Call<MarsPhotosResponseData>,
                    response: Response<MarsPhotosResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        data.value = AppState.SuccessMars(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            data.value =
                                AppState.Error(Throwable(getString(R.string.error_server_msg)))
                        } else {
                            data.value = AppState.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<MarsPhotosResponseData>, t: Throwable) {
                    data.value = AppState.Error(
                        Throwable(t.message ?: getString(R.string.error_request_msg))
                    )
                }
            })
    }
}