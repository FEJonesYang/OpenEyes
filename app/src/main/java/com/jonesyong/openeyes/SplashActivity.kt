package com.jonesyong.openeyes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.didi.drouter.api.DRouter

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DRouter.build("/home").start(this)
    }
}