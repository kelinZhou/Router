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

        private var mDefHostTab: HomeTab? = null
        private var mContext: Context? = null

        /**
         * 初始化。
         * @param defTab 打开App后默认的Tab。
         */
        fun init(context: Application, defTab: HomeTab) {
            mContext = context
            mDefHostTab = defTab
        }

        /**
         * 获取打开App后默认的Tab。
         */
        val defaultHostTab: HomeTab
            get() {
                return mDefHostTab ?: IdHomeTab(0)
            }


        internal fun getContext(): Context {
            return mContext
                ?: throw NullPointerException("You must call the Router.init() Method before use the Router")
        }

        var currentHostTab: HomeTab = defaultHostTab

        private var mRouter: Router? = null

        fun setRouter(factory: RouterFactory, jump: Boolean = false, context: Context?) {
            factory.createRouter().also {
                if (it != null) {
                    if (jump && context != null) {
                        it.jump(context)
                    } else {
                        setRouter(it)
                    }
                } else {
                    setRouter(it)
                }
            }
        }

        fun setRouter(router: Router?) {
            mRouter = router
        }

        fun setDefHostTab(tab: HomeTab) {
            if (mRouter == null) {
                setRouter(EmptyRouter(tab))
            }
        }

        fun tryJumpToTarget(context: Context) {
            val router = mRouter
            mRouter = null
            router?.jump(context)
        }

        fun getCurrentRouterTab(): HomeTab {
            return mRouter?.provideDefHostTab() ?: defaultHostTab
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

class EmptyRouter(private val defHostTab: HomeTab) : Router.SimpleRouter() {
    override fun provideDefHostTab(): HomeTab {
        return defHostTab
    }
}