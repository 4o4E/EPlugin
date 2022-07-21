@file:Suppress("UNUSED")

package top.e404.eplugin.menu.slot

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.menu.menu.Menu

/**
 * 代表一个按钮, cancel所有点击
 *
 * @property menu 持有此格子的菜单
 * @property index 在菜单中的下标
 */
abstract class MenuButton(override var menu: Menu) : MenuSlot(menu) {
    override fun onPickup(clicked: ItemStack, slot: Int, event: InventoryClickEvent) = onClick(slot, event)
    override fun onPutin(cursor: ItemStack, slot: Int, event: InventoryClickEvent) = onClick(slot, event)
    override fun onSwitch(
        clicked: ItemStack,
        cursor: ItemStack,
        slot: Int,
        event: InventoryClickEvent
    ) = onClick(slot, event)
}