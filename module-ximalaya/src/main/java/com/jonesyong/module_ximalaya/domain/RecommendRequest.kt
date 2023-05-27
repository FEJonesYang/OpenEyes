package com.jonesyong.module_ximalaya.domain

import androidx.lifecycle.MutableLiveData
import com.jonesyong.module_ximalaya.data.ResultCallBack
import com.jonesyong.module_ximalaya.data.XimalayaDataRepository
import com.ximalaya.ting.android.opensdk.model.album.Album

/**
 * @author: Jony
 * @date: 2022/5/13
 * @description: 推荐列表的请求放到这里
 */
class RecommendRequest {

    // 推荐数据的集合
    private var recommendLiveData: MutableLiveData<MutableList<Album>> = MutableLiveData()

    private var mIsLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    private var mIsFailed: MutableLiveData<Boolean> = MutableLiveData(false)

    fun isLoading(): MutableLiveData<Boolean> {
        return mIsLoading
    }

    fun isFailed(): MutableLiveData<Boolean> {
        return mIsFailed
    }

    fun getRecommendLiveData(): MutableLiveData<MutableList<Album>> {
        return recommendLiveData
    }

    // 请求推荐数据
    fun requestRecommendAlbumList() {
        XimalayaDataRepository.getInstance()
            .requestRecommendAlbumList(object : ResultCallBack.Result<MutableList<Album>> {

                override fun onSuccess(resultCallBack: ResultCallBack<MutableList<Album>?>) {
                    mIsLoading.value = false
                    mIsFailed.value = false
                    recommendLiveData.value = resultCallBack.getResult()
                }

                override fun onFailed(resultCallBack: ResultCallBack<MutableList<Album>?>) {
                    mIsLoading.value = false
                    mIsFailed.value = true
                }
            })
    }

}