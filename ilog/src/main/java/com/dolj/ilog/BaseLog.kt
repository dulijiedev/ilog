package com.dolj.ilog

/**
 * @author: dlj
 * @date: 2020/6/24
 */
class BaseLog(builder: Builder) {
    /**
     * 日志等級
     */
    var level: Level? = Level.Debug

    /**
     * 主模块
     */
    var majorModule: String? = null

    /**
     * 子模块
     */
    var subModule: String? = null

    /**
     * 业务标识 用户id等
     */
    var mark: String? = null

    /**
     * 结果
     */
    var result: String? = null

    /**
     * 参数/请求参数等
     */
    var params: String? = null

    init {
        this.level = builder.getLevel()
        this.majorModule = builder.getMajorModule()
        this.subModule = builder.getSubModule()
        this.mark = builder.getMark()
        this.result = builder.getResult()
        this.params = builder.getParams()
    }

    open class Builder {
        private var level: Level? = null
        private var majorModule: String? = null
        private var subModule: String? = null
        private var mark: String = "123"
        private var result: String? = null
        private var params: String? = null

        fun setLevel(level: Level?): Builder {
            this.level = level
            return this
        }

        fun getLevel(): Level? {
            return this.level
        }

        fun setParams(params: String?): Builder {
            this.params = params
            return this
        }

        fun getParams(): String? {
            return this.params
        }

        fun setResult(result: String?): Builder {
            this.result = result
            return this
        }

        fun getResult(): String? {
            return this.result
        }

        fun setMajorModule(majorModule: String?): Builder {
            this.majorModule = majorModule
            return this
        }

        fun getMajorModule(): String? {
            return this.majorModule
        }

        fun setSubModule(subModule: String?): Builder {
            this.subModule = subModule
            return this
        }

        fun getSubModule(): String? {
            return this.subModule
        }

        fun setMark(mark: String): Builder {
            this.mark = mark
            return this
        }

        fun getMark(): String? {
            return this.mark
        }

        fun build(): BaseLog {
            //各个参数添加到埋点中
            return BaseLog(this)
        }
    }
}