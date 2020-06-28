package com.dolj.logankit

import android.app.Application
import com.dolj.ilog.LogMgr

/**
 * @author: dlj
 * @date: 2020/6/28
 */
const val url = "http://192.168.1.189:9999/logan/upload.json"

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LogMgr.init(this, url)
    }
}