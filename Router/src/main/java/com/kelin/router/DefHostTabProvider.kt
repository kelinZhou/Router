package com.kelin.router

/**
 * **描述:** 默认的首页索引提供者。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2021/11/2 11:18 AM
 *
 * **版本:** v 1.0.0
 */
interface DefHostTabProvider {
    fun provideDefHostTab(): HomeTab
}