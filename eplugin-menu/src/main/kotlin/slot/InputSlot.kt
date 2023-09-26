@file:Suppress("UNUSED")

package top.e404.eplugin.menu.slot

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.menu.menu.ChestMenu

/**
 * 作为输入的slot(无需响应点击)
 *
 * @param menu 菜单
 */
class InputSlot(menu: ChestMenu) : MenuSlot(menu) {
    override val inv = menu.inv
    override fun onClick(slot: Int, event: InventoryClickEvent) = false
    override fun onHotbarAction(target: ItemStack?, hotbarItem: ItemStack?, slot: Int, hotbar: Int, event: InventoryClickEvent) = false
    override fun onPickup(clicked: ItemStack, slot: Int, event: InventoryClickEvent) = false
    override fun onPutin(cursor: ItemStack, slot: Int, event: InventoryClickEvent) = false
    override fun onSwitch(clicked: ItemStack, cursor: ItemStack, slot: Int, event: InventoryClickEvent) = false
}
