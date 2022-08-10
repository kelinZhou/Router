package com.kelin.router

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * **描述:** 首页导航。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2020/12/12 10:13 AM
 *
 * **版本:** v 1.0.0
 */
interface HostTab {
    /**
     * 是否是特殊Tab。
     */
    val isSpecial: Boolean

    /**
     * 名称。
     */
    @get:StringRes
    val tabLabel: Int

    /**
     * 图标。
     */
    val icon: Int

    /**
     * 要加载的页面的字节码。
     */
    val cls: Class<out Fragment>

    /**
     * 标签
     */
    val tag: String
        get() = toString()

    /**
     * 真实的名称。
     */
    val tabName: String
        get() = getString(tabLabel)
}

fun Any?.getString(@StringRes stringResId: Int, vararg formatArgs: Any): String {
    return Router.getContext().resources.let {
        if (formatArgs.isEmpty()) {
            it.getString(stringResId)
        } else {
            it.getString(stringResId, *formatArgs)
        }
    }
}