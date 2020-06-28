package com.dolj.ilog

/**
 * @author: dlj
 * @date: 2020/6/24
 */
interface LogEntity {
    fun build(): BaseLog

    fun iLog() {
        LogMgr.iLog(this)
    }
}