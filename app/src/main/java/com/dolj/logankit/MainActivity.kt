package com.dolj.logankit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dolj.ilog.Level
import com.dolj.ilog.LogMgr

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogMgr.write("testLog",Level.Debug)
    }
}
