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
        private var mContext: Application? = null

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

        /**
         * 用来记录当前的Tab。
         */
        var currentHostTab: HomeTab = defaultHostTab

        /**
         * 用来记录等待跳转到Router。
         */
        private var waitingJumpRouter: Router? = null

        /**
         * 设置一个等待跳转的Router。跳转时机取决于合适调用`tryJumpToTarget()`方法。
         * @param factory Router的创建工厂。
         * @param jump 是否立即跳转，如果为true则表示直接执行跳转，之后在调用`tryJumpToTarget()`方法则不会有任何反应，直至下一次调用该方法。
         * @param context 当jump为false则参数无意义，如果jump为true则必须传入改参数，否则无法执行跳转。
         */
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

        /**
         * 设置一个等待跳转的Router。跳转时机取决于合适调用`tryJumpToTarget()`方法。
         * @param router 等待跳转的Router。
         */
        fun setRouter(router: Router?) {
            waitingJumpRouter = router
        }

        /**
         * 如果不希望跳转到某个页面，而是仅仅切换首页的Tab则可以调用该方法。
         */
        fun setDefHostTab(tab: HomeTab) {
            if (waitingJumpRouter == null) {
                setRouter(EmptyRouter(tab))
            }
        }

        /**
         * 如果之前已经调用过`setRouter`方法则需要调用该方法执行跳转。
         */
        fun tryJumpToTarget(context: Context) {
            val router = waitingJumpRouter
            waitingJumpRouter = null
            router?.jump(context)
        }

        /**
         * 如果之前已经调用过`setRouter`方法，那么该方法则会返回调用`setRouter`方法是传入的等待跳转的Router。
         */
        fun getCurrentRouterTab(): HomeTab {
            return waitingJumpRouter?.provideDefHostTab() ?: defaultHostTab
        }
    }

    /**
     * 跳转。
     * @param context 跳转时所需要的Context，建议为Activity的Context，否则可能无法跳转。
     */
    fun jump(context: Context)

    /**
     * 一个简单的Router的实现。
     */
    class SimpleRouter(private val tab: HomeTab, private val jumper: (context: Context) -> Unit) : Router {
        override fun jump(context: Context) {
            jumper(context)
        }

        override fun provideDefHostTab(): HomeTab {
            return tab
        }
    }
}

class EmptyRouter(private val defHostTab: HomeTab) : Router {
    override fun jump(context: Context) {}

    override fun provideDefHostTab(): HomeTab {
        return defHostTab
    }
}