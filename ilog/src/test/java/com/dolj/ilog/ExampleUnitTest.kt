package com.dolj.ilog

import org.junit.Test

import org.junit.Assert.*
import java.lang.Exception

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
                .setParams(toMap())
                .setSubModule("快捷登录")
                .setResult(Result.failure(Exception("操作失败,原因：我也不知道")))
//                .setResult(Result.success("操作成功"))
                .build()
        }

        override fun toMap(): Map<String, Any> {
            return mapOf("userId" to userId, "age" to age, "active" to active)
        }

        override fun toString(): String {
            return "{userId='$userId', age=$age, active=$active}"
        }
    }

    @Test
    fun testLog(){
        val data = LoginData("1111", 18, 1)
        data.iLog()
    }
}
