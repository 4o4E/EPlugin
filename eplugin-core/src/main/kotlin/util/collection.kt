@file:Suppress("UNUSED")

package top.e404.eplugin.util

import kotlin.math.min
import kotlin.random.Random

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

/**
 * 从容器中按照一定的比重抽取指定数量的对象
 *
 * @param amount 抽取总数
 * @param repeat 是否允许重复
 * @param getWeight 获取权重
 * @return 包含选中对象的列表
 */
fun <T> Iterable<T>.select(
    amount: Int,
    repeat: Boolean,
    getWeight: (T) -> Int
): MutableList<T> {
    val list = ArrayList<T>(amount)
    // 允许重复
    if (repeat) {
        val all = sumOf { getWeight(it) }
        repeat(amount) {
            var r = Random.nextInt(all)
            for (t in this) {
                r -= getWeight(t)
                if (r <= 0) {
                    list.add(t)
                    break
                }
            }
        }
        return list
    }
    // 不允许重复
    val temp = toList()
    repeat(amount) {
        var r = Random.nextInt(temp.size)
        for (t in temp) {
            if (t in list) continue
            r -= getWeight(t)
            if (r <= 0) {
                list.add(t)
                break
            }
        }
    }
    return list
}

/**
 * 从映射中按照一定的比重抽取指定数量的对象
 *
 * @param amount 抽取总数
 * @param repeat 是否允许重复
 * @param getWeight 获取权重
 * @return 包含选中对象的映射
 */
fun <K, V> Map<K, V>.select(
    amount: Int,
    repeat: Boolean,
    getWeight: (K, V) -> Int
): MutableMap<K, V> {
    val map = HashMap<K, V>(amount)
    // 允许重复
    if (repeat) {
        val all = entries.sumOf { (k, v) -> getWeight(k, v) }
        repeat(amount) {
            var r = Random.nextInt(all)
            for ((k, v) in this) {
                r -= getWeight(k, v)
                if (r <= 0) {
                    map[k] = v
                    break
                }
            }
        }
        return map
    }
    // 不允许重复
    val temp = toList()
    repeat(amount) {
        var r = Random.nextInt(temp.size)
        for ((k, v) in temp) {
            if (v in map.values) continue
            r -= getWeight(k, v)
            if (r <= 0) {
                map[k] = v
                break
            }
        }
    }
    return map
}

/**
 * 从映射中按照一定的比重抽取指定数量的对象
 *
 * @param amount 抽取总数
 * @param repeat 是否允许重复
 * @param getWeight 获取权重
 * @return 包含选中对象的列表
 */
fun <K, V> Map<K, V>.selectWithTo(
    amount: Int,
    repeat: Boolean,
    getWeight: (K, V) -> Int
): MutableList<V> {
    val list = ArrayList<V>(amount)
    // 允许重复
    if (repeat) {
        val all = entries.sumOf { (k, v) -> getWeight(k, v) }
        repeat(amount) {
            var r = Random.nextInt(all)
            for ((k, v) in this) {
                r -= getWeight(k, v)
                if (r <= 0) {
                    list.add(v)
                    break
                }
            }
        }
        return list
    }
    // 不允许重复
    val temp = toList()
    repeat(amount) {
        var r = Random.nextInt(temp.size)
        for ((k, v) in temp) {
            if (v in list) continue
            r -= getWeight(k, v)
            if (r <= 0) {
                list.add(v)
                break
            }
        }
    }
    return list
}

/**
 * 从映射中按照一定的比重抽取指定数量的对象
 *
 * @param amount 抽取总数
 * @param repeat 是否允许重复
 * @param getWeight 获取权重
 * @return 包含选中对象的列表
 */
fun <K, V> Map<K, V>.selectByTo(
    amount: Int,
    repeat: Boolean,
    getWeight: (K, V) -> Int
): MutableList<K> {
    val list = ArrayList<K>(amount)
    // 允许重复
    if (repeat) {
        val all = entries.sumOf { (k, v) -> getWeight(k, v) }
        repeat(amount) {
            var r = Random.nextInt(all)
            for ((k, v) in this) {
                r -= getWeight(k, v)
                if (r <= 0) {
                    list.add(k)
                    break
                }
            }
        }
        return list
    }
    // 不允许重复
    val temp = toList()
    repeat(amount) {
        var r = Random.nextInt(temp.size)
        for ((k, v) in temp) {
            if (k in list) continue
            r -= getWeight(k, v)
            if (r <= 0) {
                list.add(k)
                break
            }
        }
    }
    return list
}