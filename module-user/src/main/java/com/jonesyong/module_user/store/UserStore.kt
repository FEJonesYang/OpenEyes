package com.jonesyong.module_user.store

import com.jonesyong.library_base.application.ApplicationProvider
import com.tencent.mmkv.MMKV

object UserStore {

    private const val KEY_USERNAME = "username"

    private val mmkv: MMKV by lazy {
        MMKV.initialize(ApplicationProvider.getContext())
        MMKV.defaultMMKV()
    }

    fun saveUser(username: String) {
        mmkv.encode(KEY_USERNAME, username)
    }

    fun getUsername(): String? = mmkv.decodeString(KEY_USERNAME)

    fun clear() {
        mmkv.removeValueForKey(KEY_USERNAME)
    }
}
