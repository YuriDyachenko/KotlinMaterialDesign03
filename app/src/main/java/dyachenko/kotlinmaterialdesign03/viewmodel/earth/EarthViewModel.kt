package dyachenko.kotlinmaterialdesign03.viewmodel.earth

import dyachenko.kotlinmaterialdesign03.BuildConfig
import dyachenko.kotlinmaterialdesign03.viewmodel.CommonViewModel

class EarthViewModel : CommonViewModel() {

    fun getEarthPhotoFromServer(dateString: String, lon: Float, lat: Float, dim: Float) {
        getFromServer(
            retrofitImpl
                .getEarthRetrofitImpl()
                .getEarthPhoto(lon, lat, dim, BuildConfig.NASA_API_KEY, dateString)
        )
    }
}