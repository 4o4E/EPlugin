package top.e404.eplugin.menu

import org.bukkit.inventory.ItemStack

interface Displayable {
    val item: ItemStack
    var needUpdate: Boolean
    fun update()
}
