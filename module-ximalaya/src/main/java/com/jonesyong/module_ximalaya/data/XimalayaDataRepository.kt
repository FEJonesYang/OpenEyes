package com.jonesyong.module_ximalaya.data

import com.jonesyong.library_foundation.util.log.LogUtils
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack
import com.ximalaya.ting.android.opensdk.model.album.Album
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList


/**
 * @author: Jony
 * @date: 2022/5/13
 * @description:
 */
class XimalayaDataRepository private constructor() {

    // kotlin double check 的写法
    companion object {
        private val instances: XimalayaDataRepository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            XimalayaDataRepository()
        }

        fun getInstance(): XimalayaDataRepository {
            return instances
        }
    }

    // 真正发起请求的地方
    fun requestRecommendAlbumList(resultCallBack: ResultCallBack.Result<MutableList<Album>>) {
        val map = mutableMapOf<String?, String?>()
        map[DTransferConstants.LIKE_COUNT] = 20.toString()
        CommonRequest.getGuessLikeAlbum(map, object : IDataCallBack<GussLikeAlbumList?> {
            override fun onSuccess(data: GussLikeAlbumList?) {
                resultCallBack.onSuccess(ResultCallBack(data?.albumList, ResponseStatus(200, true)))
                LogUtils.logSuccess("RecommendFragment", data?.albumList?.size.toString())
            }

            override fun onError(errorCode: Int, errorMessage: String?) {
                resultCallBack.onFailed(ResultCallBack(null, ResponseStatus(errorCode, false)))
                LogUtils.logError("RecommendFragment", errorCode, errorMessage)
            }
        })
    }

}