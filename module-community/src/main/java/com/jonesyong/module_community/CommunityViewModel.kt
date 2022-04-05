package com.jonesyong.module_community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @Author JonesYang
 * @Date 2022-02-07
 * @Description
 */
class CommunityViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is community Fragment"
    }
    val text: LiveData<String> = _text
}