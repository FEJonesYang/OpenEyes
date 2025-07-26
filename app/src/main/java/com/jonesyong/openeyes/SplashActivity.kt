package com.jonesyong.openeyes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jonesyong.module_home.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this,MainActivity::class.java))
//        ARouter.getInstance().build("/test/activity").navigation()
    }
}