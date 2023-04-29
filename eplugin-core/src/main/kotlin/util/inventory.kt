package top.e404.eplugin.util

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

inline fun Inventory.editItem(index: Int, block: (ItemStack?) -> ItemStack) = block(getItem(index))
