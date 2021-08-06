package dyachenko.kotlinmaterialdesign03.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dyachenko.kotlinmaterialdesign03.model.retrofit.RetrofitImpl

open class CommonViewModel : ViewModel() {
    protected val data: MutableLiveData<AppState> = MutableLiveData()
    protected val retrofitImpl = RetrofitImpl()
    private var stringResources: ((Int) -> String)? = null

    fun getLiveData() = data

    fun setStringResources(stringResources: (Int) -> String) {
        this.stringResources = stringResources
    }

    protected fun getString(id: Int) = stringResources?.invoke(id)
}