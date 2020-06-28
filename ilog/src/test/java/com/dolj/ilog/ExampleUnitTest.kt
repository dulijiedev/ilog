package com.dolj.ilog

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    class LoginData(val userId: String, val age: Int, val active: Int) : LogEntity {
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

    @Test
    fun testLog(){
        val data = LoginData("1111", 18, 1)
//        data.iLog()
    }
}
