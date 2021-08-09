package dyachenko.kotlinmaterialdesign03.viewmodel.mars

import dyachenko.kotlinmaterialdesign03.BuildConfig
import dyachenko.kotlinmaterialdesign03.model.settings.SettingsData
import dyachenko.kotlinmaterialdesign03.viewmodel.CommonViewModel

class MarsViewModel : CommonViewModel() {

    fun getMarsPhotosFromServer(dateString: String) {
        getFromServer(
            retrofitImpl
                .getMarsRetrofitImpl()
                .getMarsPhotos(SettingsData.marsRoverCamera, BuildConfig.NASA_API_KEY, dateString)
        )
    }
}