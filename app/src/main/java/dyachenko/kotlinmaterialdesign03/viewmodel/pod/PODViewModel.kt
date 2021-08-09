package dyachenko.kotlinmaterialdesign03.viewmodel.pod

import dyachenko.kotlinmaterialdesign03.BuildConfig
import dyachenko.kotlinmaterialdesign03.viewmodel.CommonViewModel

class PODViewModel : CommonViewModel() {

    fun getPODFromServer(dateString: String) {
        getFromServer(
            retrofitImpl
                .getPODRetrofitImpl()
                .getPOD(BuildConfig.NASA_API_KEY, dateString)
        )
    }
}