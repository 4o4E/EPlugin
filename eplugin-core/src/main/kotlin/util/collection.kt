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
fun <T> Collection<T>.select(
    amount: Int,
    repeat: Boolean,
    getWeight: (T) -> Int,
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
    if (size < amount) throw IllegalArgumentException("size($size) , amount($amount)")
    val temp = toMutableList()
    while (list.size < amount) {
        var r = Random.nextInt(temp.sumOf { getWeight(it) })
        val iter = temp.iterator()
        while (iter.hasNext()) {
            val t = iter.next()
            r -= getWeight(t)
            if (r <= 0) {
                list.add(t)
                iter.remove()
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
    getWeight: (K, V) -> Int,
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
    if (size < amount) throw IllegalArgumentException("size($size) , amount($amount)")
    val temp = toList().asMutableList()
    while (map.size < amount) {
        var r = Random.nextInt(temp.sumOf { (k, v) -> getWeight(k, v) })
        val iter = temp.iterator()
        while (iter.hasNext()) {
            val (k, v) = iter.next()
            r -= getWeight(k, v)
            if (r <= 0) {
                map[k] = v
                iter.remove()
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
    getWeight: (K, V) -> Int,
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
    if (size < amount) throw IllegalArgumentException("size($size) , amount($amount)")
    val temp = toList().asMutableList()
    while (list.size < amount) {
        var r = Random.nextInt(temp.sumOf { (k, v) -> getWeight(k, v) })
        val iter = temp.iterator()
        while (iter.hasNext()) {
            val (k, v) = iter.next()
            r -= getWeight(k, v)
            if (r <= 0) {
                list.add(v)
                iter.remove()
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
    getWeight: (K, V) -> Int,
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
    if (size < amount) throw IllegalArgumentException("size($size) , amount($amount)")
    val temp = toList().asMutableList()
    while (list.size < amount) {
        var r = Random.nextInt(temp.sumOf { (k, v) -> getWeight(k, v) })
        val iter = temp.iterator()
        while (iter.hasNext()) {
            val (k, v) = iter.next()
            r -= getWeight(k, v)
            if (r <= 0) {
                list.add(k)
                iter.remove()
                break
            }
        }
    }
    return list
}

/**
 * 判断列表t和r是否一一对应
 *
 * @param condition 判断t, r是否对应
 */
fun <T, R> matches(
    tList: Collection<T>,
    rList: Collection<R>,
    condition: (T, R) -> Boolean,
): Boolean {
    if (tList.size != rList.size) return false
    val tl = tList.toMutableList()
    val rl = rList.toMutableList()
    val ti = tl.iterator()
    a@ for (t in ti) {
        val ri = rl.iterator()
        for (r in ri) if (condition(t, r)) {
            ti.remove()
            ri.remove()
            continue@a
        }
        return false
    }
    return true
}

fun <T : Any> Collection<T>.merge(
    prefix: T?,
    separator: T,
    suffix: T?,
): MutableList<T> {
    val iterator = iterator()
    var size = size * 2 - 1
    prefix?.let { ++size }
    suffix?.let { ++size }
    val list = ArrayList<T>(size)
    prefix?.let { list.add(it) }
    while (true) {
        val next = iterator.next()
        list.add(next)
        if (!iterator.hasNext()) break
        list.add(separator)
    }
    suffix?.let { list.add(it) }
    return list
}

fun <T> List<T>.asMutableList() = when (this) {
    is MutableList<T> -> this
    else -> ArrayList(this)
}

fun <T, R> Iterable<T>.mapMutable(transform: (T) -> R): MutableList<R> {
    return mapTo(ArrayList(if (this is Collection<*>) this.size else 10), transform)
}

fun <T, R> Collection<T>.mapMutable(transform: (T) -> R): MutableList<R> {
    if (this is MutableList) return mapMutable(transform)
    return mapTo(ArrayList(size), transform)
}

@Suppress("UNCHECKED_CAST")
fun <T, R> MutableList<T>.mapMutable(transform: (T) -> R): MutableList<R> {
    val list = this as MutableList<R>
    forEachIndexed { index, t ->
        list[index] = transform(t)
    }
    return list
}

inline fun <reified T> Iterable<Any>.firstIsInstance() = first { it is T } as T
inline fun <reified T> Iterable<Any>.firstIsInstanceOrNull() = firstOrNull { it is T } as T?
inline fun <reified T> Iterable<Any>.anyIsInstance() = any { it is T }

fun <T> Iterable<T>.alsoRemove(vararg target: T) = run {
    if (this is MutableCollection) {
        target.forEach(::remove)
        return@run this
    }
    toMutableList().also { target.forEach(it::remove) }
}

fun <T> Iterable<T>.alsoRemove(condition: (T) -> Boolean) = run {
    if (this is MutableCollection) {
        this.removeIf(condition)
        return@run this
    }
    toMutableList().also { list ->
        list.removeIf(condition)
    }
}

fun <T> MutableCollection<T>.removeFirst(condition: (T) -> Boolean): T? {
    val iter = iterator()
    while (iter.hasNext()) {
        val next = iter.next()
        if (condition(next)) {
            iter.remove()
            return next
        }
    }
    return null
}
