@file:Suppress("UNUSED")

package top.e404.eplugin.table

import kotlin.random.Random

interface Tableable<T> {
    val weight: Int
    fun generator(): T
}

/**
 * 从列表中按照权重随机选择指定数量的元素并生成
 *
 * @param amount 选择的元素数量
 * @param repeat 是否允许重复选择同一元素
 * @return 由选择元素生成的列表
 */
fun <T> Collection<Tableable<T>>.choose(
    amount: Int,
    repeat: Boolean = true
): ArrayList<T> {
    var all = sumOf { it.weight }
    val collection = ArrayList<T>(amount)
    // 允许重复
    if (repeat) {
        while (collection.size < amount) {
            var t = Random.nextInt(all) + 1
            for (item in this) {
                t -= item.weight
                if (t <= 0) {
                    collection.add(item.generator())
                    continue
                }
            }
        }
        return collection
    }
    // 不允许重复
    val list = toMutableList()
    while (collection.size < amount) {
        var t = Random.nextInt(all) + 1
        for (item in this) {
            t -= item.weight
            if (t <= 0) {
                list.remove(item)
                all -= item.weight
                collection.add(item.generator())
                continue
            }
        }
    }
    return collection
}


/**
 * 从列表按照权重随机挑选一个
 *
 * @return 选择的元素
 */
fun <T> Collection<Tableable<T>>.choose(): T {
    var random = Random.nextInt(0, sumOf { it.weight }) + 1
    for (t in this) {
        random -= t.weight
        if (random <= 0) return t.generator()
    }
    return last().generator()
}

/**
 * 从列表按照权重随机挑选一个
 *
 * @param getWeight 获取权重 获取的权重不可低于0
 * @return 选择的元素
 */
fun <T> Iterable<T>.chooseBy(getWeight: (T) -> Int): T {
    var random = Random.nextInt(0, sumOf { getWeight(it) }) + 1
    for (t in this) {
        random -= getWeight(t)
        if (random <= 0) return t
    }
    throw Exception()
}

/**
 * 从列表按照权重随机挑选一个
 *
 * @param amount 数量
 * @param repeat 是否允许重复选择同一元素
 * @param getWeight 获取权重 获取的权重不可低于0
 * @return 选择的元素
 */
fun <T> Iterable<T>.chooseBy(
    amount: Int,
    repeat: Boolean = true,
    getWeight: (T) -> Int
): List<T> {
    var all = sumOf { getWeight(it) }
    val collection = ArrayList<T>(amount)
    // 允许重复
    if (repeat) {
        while (collection.size < amount) {
            var t = Random.nextInt(all) + 1
            for (item in this) {
                t -= getWeight(item)
                if (t <= 0) {
                    collection.add(item)
                    continue
                }
            }
        }
        return collection
    }
    // 不允许重复
    val list = toMutableList()
    while (collection.size < amount) {
        var t = Random.nextInt(all) + 1
        for (item in this) {
            t -= getWeight(item)
            if (t <= 0) {
                list.remove(item)
                all -= getWeight(item)
                collection.add(item)
                continue
            }
        }
    }
    return collection
}
