package top.e404.eplugin.inventory

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * 统计背包中的匹配的物品数量
 *
 * @param condition 判断物品是否匹配
 * @return 匹配的物品的总数
 */
fun Inventory.count(condition: (ItemStack) -> Boolean) = sumOf { if (condition(it)) it.amount else 0 }

/**
 * 从背包中取走指定数量的物品
 *
 * @param amount 数量
 * @param condition 判断物品是否匹配
 * @return 是否成功(若匹配的物品的总数少于数量则失败)
 */
fun Inventory.take(amount: Int, condition: (ItemStack) -> Boolean): Boolean {
    // 若匹配的物品的总数少于数量则失败
    if (count(condition) < amount) return false
    var tmp = amount
    forEachIndexed { index, item ->
        if (!condition(item)) return@forEachIndexed
        if (item.amount <= tmp) {
            tmp -= item.amount
            setItem(index, ItemStack(Material.AIR))
            return@forEachIndexed
        }
        item.amount -= tmp
        setItem(index, item)
        return true
    }
    return true
}