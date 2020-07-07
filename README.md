### Logan日志封装 [![](https://jitpack.io/v/sdohubs/ilog.svg)](https://jitpack.io/#sdohubs/ilog)

整体类图结构如下
![类图](https://raw.githubusercontent.com/sdohubs/ilog/master/log.png)
iOS实现方式见 https://github.com/henryhongli/iLogKit.git
##### 1.Level日志等级

debug(一般)，Info(正常)，Warn(异常)，Error(错误)

##### 2.LogResult为日志结果

成功或失败，如果出错时，可以使用desc方法设置描述内容。

```
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
```

##### 3.BaseLog日志参数封装实体

主要存放了日志所需要的参数信息，内部使用Builder构建这模式创建。

##### 4.LogEntity日志构建及写入接口

其他实体类可以通过实现此接口来通过buid方法，创建日志参数，iLog方法写入日志。

```
interface LogEntity {
    fun build(): BaseLog

    fun iLog() {
        LogMgr.iLog(this)
    }
}
```

eg:

```
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
val data = LoginData("1111", 18, 1)
data.iLog()
输出：
[登录][uid][快捷登录](操作失败,原因:1111) ({username:String,password:String}) LoginData(userId='1111', age=18, active=1)
```

##### 5.LogMgr日志管理类

主要是通过此类，初始化日志，写入日志，上传日志，刪除上传后的日志，设置日志最低等级/上传URL/本地日志路径等操作。通过添加iLog扩展函数防止调用混淆。

###### 1初始化方法

```
init(application: Application, sendUrl: String) 传入Application 及上传URL
```

如果想要自定本地日志存储路径可以通过 `setPath`方法设置。

###### 2.设置日志等级方法

通过此方法设置最低日志等级，低于此等级不会写入日志文件。

```
setLevel(level: Level) 
```

###### 3.写入日志实体

```
write(logEntity: LogEntity) 
```

通过此方法写入集成`LogEntity`的实体类，完成日志写入。

如果无实体类写入可通过以下方法写入日志字符串。

```
write(logStr: String, level: Level?)
```

###### 4.上传日志

通过此方法上传日志到服务器，内部包含了上传 手机品牌/版本号/收集品牌/日志文件名，及上传后日志文件刪除方法。

```
fun s()
```

###### 5.日志刪除

此方法用于日志文件的刪除，清理。

```
 fun deleteFile()
```

##### 6.使用方式

添加依赖库
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```
implementation 'com.github.sdohubs:ilog:1.0.0'
```

在Application 中初始化

```
const val url = "http://192.168.1.189:9999/logan/upload.json"
LogMgr.init(application,url)
```

如果想要实现对应日志库的功能，需要修改LogMgr内初始化/写入/刪除/上传等对应的库方法，以此实现日志上传功能
