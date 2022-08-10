package com.kelin.router

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
    fun createRouter(): Router?
}