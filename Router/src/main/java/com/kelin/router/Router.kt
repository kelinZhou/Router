package com.kelin.router

import android.app.Application
import android.content.Context

/**
 * **描述:** 跳转路由。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2021/10/8 9:40 AM
 *
 * **版本:** v 1.0.0
 */
interface Router : DefHostTabProvider {


    companion object {
        private var mContext: Context? = null

        fun init(context: Application) {
            mContext = context
        }

        internal fun getContext(): Context {
            return mContext?: throw NullPointerException("You must call the Router.init() Method before use the Router")
        }
    }

    /**
     * 跳转。
     * @param context 跳转时所需要的Context，建议为Activity的Context，否则可能无法跳转。
     */
    fun jump(context: Context)

    abstract class SimpleRouter : Router {
        override fun jump(context: Context) {}
    }
}

class EmptyRouter(private val defHostTab: HostTab) : Router.SimpleRouter() {
    override fun provideDefHostTab(): HostTab {
        return defHostTab
    }
}