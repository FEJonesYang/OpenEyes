package com.jonesyong.module_community

import androidx.lifecycle.MutableLiveData
import com.jonesyong.library_base.model.BaseViewModel
import java.io.Serializable

/**
 * @Author JonesYang
 * @Date 2022-02-07
 * @Description
 */
class CommunityViewModel : BaseViewModel() {

    val pageList = MutableLiveData<MutableList<PageData>>()

    override fun loadData() {
        pageList.value = mutableListOf<PageData>().apply {
            add(PageData(name = "推荐", type = "recommend"))
            add(PageData(name = "关注", type = "follow"))
        }
    }

}

class PageData(val name: String, val type: String) : Serializable
