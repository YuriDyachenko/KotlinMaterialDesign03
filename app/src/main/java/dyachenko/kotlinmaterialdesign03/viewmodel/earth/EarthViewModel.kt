package dyachenko.kotlinmaterialdesign03.viewmodel.earth

import dyachenko.kotlinmaterialdesign03.BuildConfig
import dyachenko.kotlinmaterialdesign03.R
import dyachenko.kotlinmaterialdesign03.model.earth.EarthResponseData
import dyachenko.kotlinmaterialdesign03.viewmodel.AppState
import dyachenko.kotlinmaterialdesign03.viewmodel.CommonViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthViewModel : CommonViewModel() {

    fun getEarthPhotoFromServer(dateString: String, lon: Float, lat: Float, dim: Float) {
        data.value = AppState.Loading
        val apiKey: String = BuildConfig.NASA_API_KEY
        retrofitImpl.getEarthRetrofitImpl().getEarthPhoto(lon, lat, dim, apiKey, dateString)
            .enqueue(object :
                Callback<EarthResponseData> {
                override fun onResponse(
                    call: Call<EarthResponseData>,
                    response: Response<EarthResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        data.value = AppState.SuccessEarth(response.body()!!)
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

                override fun onFailure(call: Call<EarthResponseData>, t: Throwable) {
                    data.value = AppState.Error(
                        Throwable(t.message ?: getString(R.string.error_request_msg))
                    )
                }
            })
    }
}