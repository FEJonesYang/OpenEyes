package com.jonesyong.module_ximalaya.ui.state

import androidx.lifecycle.ViewModel
import com.jonesyong.module_ximalaya.domain.RecommendRequest

/**
 * @author: Jony
 * @date: 2022/5/11
 * @description:
 */
class RecommendViewModel : ViewModel() {
    internal val recommendRequest: RecommendRequest = RecommendRequest()
}