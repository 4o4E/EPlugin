@file:Suppress("UNUSED")

package top.e404.eplugin.util

import org.bukkit.Material
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