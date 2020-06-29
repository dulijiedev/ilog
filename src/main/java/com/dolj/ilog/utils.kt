package com.dolj.ilog

import android.content.Context
import android.os.Build
import android.widget.Toast
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author: dlj
 * @date: 2020/6/28
 */
fun getModel(): String {
    var model: String? = Build.MODEL
    if (model != null) {
        model = model.trim { it <= ' ' }.replace("\\s*".toRegex(), "")
    } else {
        model = ""
    }
    return model
}

/**
 * 获取当前app的版本号
 *
 * @param context 上下文
 */
fun getVersionName(context: Context): String? {
    return try {
        val packageManager = context.packageManager
        val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName
    } catch (e: Exception) {
        e.printStackTrace()
        "1.0.0"
    }
}

fun toast(context: Context, duration: Int = Toast.LENGTH_SHORT, value: () -> String) =
    Toast.makeText(context, value(), duration).show()

fun toast(context: Context, id: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(context, context.getString(id), duration).show()

inline fun Context.toast(
    context: Context,
    duration: Int = Toast.LENGTH_SHORT,
    value: () -> String
) =
    Toast.makeText(this, value(), duration).show()

fun <T> observableIO2Main(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}