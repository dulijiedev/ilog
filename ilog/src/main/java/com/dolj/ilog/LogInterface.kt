package com.dolj.ilog

import android.app.Application

/**
 * @author: dlj
 * @date: 2020/6/29
 */
interface LogInterface {

    open var MIN_LEVEL: Level

    /**
     * 上传url
     */
    open var SEND_URL: String

    /**
     * application
     */
    open var app: Application?

    /**
     * 日志文件本地路径
     */
    open var path: String?

    /**
     * 初始化
     */
    fun init(application: Application, sendUrl: String)

    /**
     * 写入日志
     * @param logEntity 日志实体
     */
    fun write(logEntity: LogEntity)

    /**
     * 写入日志
     * @param logStr 日志字符串
     * @param level 日志等级
     */
    fun write(logStr: String, level: Level?)

    /**
     * 上传日志
     */
    fun s()

    /**
     * 刪除日志文件
     */
    fun deleteFile()

}