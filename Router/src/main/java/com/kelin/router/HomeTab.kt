package com.kelin.router

/**
 * **描述:** 有位置的Tab。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2022/8/10 5:07 PM
 *
 * **版本:** v 1.0.0
 */
interface HomeTab {
    val id: Int
}

internal class IdHomeTab(override val id: Int) : HomeTab