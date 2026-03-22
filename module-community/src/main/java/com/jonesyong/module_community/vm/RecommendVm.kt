package com.jonesyong.module_community.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jonesyong.library_base.ktx.safeLaunch
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.library_openeyes.model.OpenEyeResponse
import com.jonesyong.module_community.api.model.CommunityData
import com.jonesyong.module_community.api.request.RecommendRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class RecommendVm : BaseViewModel() {

    private val request = RecommendRequest(this)
    val recommendData = MutableLiveData<OpenEyeResponse<CommunityData>?>()

    override fun loadData() {
        viewModelScope.launch {
            beforeRequest()


            request.fetchData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }



            val data = safeLaunch {
                request.fetchRecommendData()
            }
            afterRequest(data != null) {
                recommendData.value = data
            }
        }
    }
}