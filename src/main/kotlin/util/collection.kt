package top.e404.eplugin.util

import kotlin.math.min

fun <T> Collection<T>.splitBySize(size: Int): MutableList<out MutableList<T>> {
    val list = ArrayList<ArrayList<T>>()
    var tmp = ArrayList<T>(size)
    val iterator = iterator()
    while (iterator.hasNext()) {
        tmp.add(iterator.next())
        if (tmp.size >= size) {
            list.add(tmp)
            tmp = ArrayList(size)
        }
    }
    if (tmp.isNotEmpty()) list.add(tmp)
    return list
}

fun <T> Collection<T>.splitByPage(pageSize: Int, page: Int): MutableList<T> {
    require(pageSize > 0)
    require(page >= 0)
    val start = page * pageSize
    if (start >= size) return mutableListOf()
    // 结束的下标, 不包含
    val end = min(start + pageSize, size)
    val list = ArrayList<T>(end - start)
    var index = 0
    val iterator = iterator()
    while (iterator.hasNext()) {
        // start前的元素
        if (index < start) {
            index++
            iterator.next()
            continue
        }
        // end后的元素
        if (index >= end) break
        // 目标元素
        list.add(iterator.next())
        index++
    }
    return list
}

fun getPageCount(size: Int, pageSize: Int): Int {
    val a = size / pageSize
    return if (size % pageSize == 0) a else a + 1
}