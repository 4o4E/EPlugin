@file:Suppress("UNUSED")

package top.e404.eplugin.util

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import top.e404.eplugin.EPlugin.Companion.color

var ItemStack.lore
    get() = itemMeta?.lore ?: emptyList()
    set(lore) {
        editItemMeta { this.lore = lore }
    }

var ItemStack.name: String?
    get() = itemMeta?.displayName
    set(name) {
        editItemMeta { this.setDisplayName(name) }
    }

fun ItemStack.editItemMeta(block: ItemMeta.() -> Unit) = apply {
    val im = itemMeta ?: ItemStack(type).itemMeta!!
    im.block()
    itemMeta = im
}

val emptyItem: ItemStack
    get() = ItemStack(Material.AIR)

fun buildItemStack(
    type: Material,
    amount: Int = 1,
    name: String?,
    lore: List<String>? = null,
    block: ItemMeta.() -> Unit = {}
) = ItemStack(type, amount).editItemMeta {
    name?.let { setDisplayName(it.color()) }
    lore?.let { list -> this.lore = list.map { it.color() } }
    block()
}

/**
 * 给予玩家物品, 自动堆叠
 *
 * @param item 给予的物品
 * @return 若背包已满则掉落并返回false
 */
fun Player.giveStickItem(
    item: ItemStack,
): Boolean {
    val maxStackSize = item.maxStackSize
    // 先检查可堆叠
    for ((index, invItem) in inventory.withIndex()) {
        // 背包格子为空 || 物品不相同
        if (invItem == null || !item.isSimilar(invItem)) continue
        val stickAmount = maxStackSize - invItem.amount // 可以堆叠的数量
        // 直接堆叠在物品上
        if (stickAmount >= item.amount) {
            invItem.amount += item.amount
            inventory.setItem(index, invItem)
            return true
        }
        // 有溢出
        item.amount -= stickAmount
        invItem.amount += stickAmount
        inventory.setItem(index, invItem)
    }
    return giveItem(item)
}

/**
 * 给予玩家物品, 不检查堆叠
 *
 * @param item 给予的物品
 * @return 若背包已满则掉落并返回false
 */
fun Player.giveItem(
    item: ItemStack,
): Boolean {
    val firstEmpty = inventory.firstEmpty()
    if (firstEmpty == -1) {
        world.dropItem(location, item)
        return false
    }
    inventory.setItem(firstEmpty, item)
    return true
}