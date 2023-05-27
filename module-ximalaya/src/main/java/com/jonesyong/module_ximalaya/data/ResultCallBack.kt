package com.jonesyong.module_ximalaya.data


/**
 * @author: Jony
 * @date: 2022/5/13
 * @description:
 */
class ResultCallBack<T> constructor(
    private val mEntity: T,
    private val responseStatus: ResponseStatus
) {

    fun getResult(): T {
        return mEntity
    }

    interface Result<T> {
        fun onSuccess(resultCallBack: ResultCallBack<T?>)

        fun onFailed(resultCallBack: ResultCallBack<T?>)
    }

}