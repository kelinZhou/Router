package com.kelin.router

import android.content.Context

/**
 * **描述:** 路由工厂。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2021/10/8 9:52 AM
 *
 * **版本:** v 1.0.0
 */
interface RouterFactory {
    companion object

    fun createRouter(): Router?
}


/**
 * 创建的Router的实现，具体跳转逻辑由调用者决定。
 */
fun RouterFactory.Companion.create(tab: HomeTab = Router.currentHostTab, jumper: (context: Context) -> Unit): Router {
    return Router.SimpleRouter(tab, jumper)
}