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
            //forEach {
            //    if (Random.nextInt(all) < it.weight) collection.add(it.generator())
            //    if (collection.size >= amount) return@forEach
            //}
            //return collection
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