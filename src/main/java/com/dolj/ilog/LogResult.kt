package com.dolj.ilog

/**
 * @author: dlj
 * @date: 2020/6/24
 */
enum class LogResult(val value: String) {
    Success("操作成功"),
    Failure("操作失败");

    fun desc(value: String? = ""): String {
        return when (this) {
            Success -> Success.value
            Failure -> "${Failure.value},原因:$value"
        }
    }
}
