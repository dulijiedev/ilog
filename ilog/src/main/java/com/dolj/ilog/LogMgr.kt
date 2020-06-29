package com.dolj.ilog

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.dianping.logan.Logan
import com.dianping.logan.LoganConfig
import io.reactivex.Observable.just
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: dlj
 * @date: 2020/6/24
 * 使用1
 * const val url = "http://192.168.1.189:9999/logan/upload.json"
class MyApp : Application() {
override fun onCreate() {
super.onCreate()
LogMgr.init(this, url)
}
 *
 * LogMgr.write("testLog",Level.Debug)
 * ********************************************
 * 使用1
 * class LoginData(val userId: String, val age: Int, val active: Int) : LogEntity {
override fun build(): BaseLog {
return BaseLog
.Builder()
.setLevel(Level.Debug)
.setMajorModule("登录")
.setMark("uid")
.setParams("{username:String,password:String}")
.setSubModule("快捷登录")
.setResult(LogResult.Failure.desc("1111"))
.build()
}

override fun toString(): String {
return "LoginData(userId='$userId', age=$age, active=$active)"
}
}

val data = LoginData("1111", 18, 1)
data.iLog()
 */

/**
 * 添加命名控件
 */
fun LogMgr.iLog(logEntity: LogEntity) {
    LogMgr.write(logEntity)
}

/**
 * log管理
 */
object LogMgr :LogInterface{

    override var MIN_LEVEL = Level.Debug

    /**
     * 上传url
     */
    override var SEND_URL: String = ""

    /**
     * application
     */
    override var app: Application? =null

    /**
     * 日志文件本地路径
     */
    override var path: String? = null

    /**
     *初始化日志 在Application中
     * @param application
     */
    override fun init(application: Application, sendUrl: String) {
        val config = LoganConfig.Builder()
            .setCachePath(application.filesDir.absolutePath)
            .setPath(
                (application.getExternalFilesDir(null)?.absolutePath
                        + File.separator) + "logan_v1"
            )
            .setEncryptKey16("0123456789012345".toByteArray())
            .setEncryptIV16("0123456789012345".toByteArray())
            .setMaxFile(100)
            .setDay(1)
            .build()
        Logan.init(config)
        this.SEND_URL = sendUrl
        this.app = application
        path = application.getExternalFilesDir(null)?.absolutePath + File.separator + "logan_v1"
    }

    /**
     * 设置日志等级
     */
    fun setLevel(level: Level) {
        this.MIN_LEVEL = level
    }

    /**
     * 写入log
     * @param logEntity log实体
     */
    override fun write(logEntity: LogEntity) {
        val log = logEntity.build()
        if (log.level != null && log.level!!.value >= MIN_LEVEL.value) {
            val logStr =
                "[${log.majorModule}][${log.mark}][${log.subModule}](${log.result}) ${if (log.params != null) "(${log.params})" else ""} $logEntity"
            println(logStr)
            Logan.w(logStr, log.level?.value ?: 1)
        }
    }

    override fun write(logStr: String, level: Level?) {
        if (level != null && level.value >= MIN_LEVEL.value) {
            Logan.w(logStr, level.value)
        }
    }

    /**
     * 上传文件
     */
    @SuppressLint("SimpleDateFormat", "LogNotTimber")
    override fun s() {
        val map = Logan.getAllFilesInfo()
        if (!map.isNullOrEmpty()) {
            Logan.s(
                SEND_URL,
                SimpleDateFormat("yyyy-MM-dd").format(Date()),
                app!!.applicationContext.packageName,
                "Android",
                getModel(),
                android.os.Build.VERSION.RELEASE,
                getVersionName(app!!.applicationContext)
            ) { statusCode, data ->
                val resultData = if (data != null) String(data) else ""
                Log.d(TAG, "upload result, httpCode: $statusCode, details: $resultData")
                io.reactivex.Observable.just(statusCode)
                    .compose(observableIO2Main())
                    .subscribe {
                        val text = if (it == 200) "上传成功" else "上传失败 $resultData"
                        toast(app!!.applicationContext, Toast.LENGTH_SHORT) { text }
                        deleteFile()
                    }
            }
        } else {
            toast(app!!.applicationContext, Toast.LENGTH_SHORT) { "暂无日志信息" }
        }
    }

    /**
     * 刪除本地log文件
     */
    @SuppressLint("CheckResult")
    override fun deleteFile() {
        io.reactivex.Observable.create<Boolean> {
            if (path != null) {
                val file = File(path!!)
                if (file.exists()) {
                    val result = file.deleteRecursively()
                    it.onNext(result)
                }
                it.onComplete()
            }
        }.compose(observableIO2Main())
            .subscribe {
                toast(app!!.applicationContext, Toast.LENGTH_SHORT) { "日志文件已删除" }
            }
    }

}