package dyachenko.kotlinmaterialdesign03.viewmodel.pod

import dyachenko.kotlinmaterialdesign03.BuildConfig
import dyachenko.kotlinmaterialdesign03.R
import dyachenko.kotlinmaterialdesign03.model.pod.PODResponseData
import dyachenko.kotlinmaterialdesign03.viewmodel.AppState
import dyachenko.kotlinmaterialdesign03.viewmodel.CommonViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PODViewModel : CommonViewModel() {

    fun getPODFromServer(dateString: String) {
        data.value = AppState.Loading
        val apiKey = BuildConfig.NASA_API_KEY
        retrofitImpl.getPODRetrofitImpl().getPOD(apiKey, dateString).enqueue(object :
            Callback<PODResponseData> {
            override fun onResponse(
                call: Call<PODResponseData>,
                response: Response<PODResponseData>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    data.value = AppState.SuccessPOD(response.body()!!)
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

            override fun onFailure(call: Call<PODResponseData>, t: Throwable) {
                data.value = AppState.Error(
                    Throwable(t.message ?: getString(R.string.error_request_msg))
                )
            }
        })
    }
}