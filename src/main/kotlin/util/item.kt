@file:Suppress("UNUSED")

package top.e404.eplugin.util

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

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