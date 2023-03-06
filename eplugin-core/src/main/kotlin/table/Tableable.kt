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
            var t = Random.nextInt(all)
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
        var t = Random.nextInt(all)
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
    var random = Random.nextInt(0, sumOf { it.weight })
    for (t in this) {
        random -= t.weight
        if (random <= 0) return t.generator()
    }
    return last().generator()
}