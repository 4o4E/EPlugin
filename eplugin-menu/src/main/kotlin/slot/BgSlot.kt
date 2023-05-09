@file:Suppress("UNUSED")

package top.e404.eplugin.menu.slot

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.menu.menu.ChestMenu

/**
 * 作为背景的slot(无需响应点击)
 *
 * @param menu 菜单
 */
class BgSlot(menu: ChestMenu, override var item: ItemStack) : MenuSlot(menu) {
    override val inv = menu.inv
    override fun onClick(slot: Int, event: InventoryClickEvent) = true
    override fun onHotbarAction(target: ItemStack?, hotbarItem: ItemStack?, slot: Int, hotbar: Int, event: InventoryClickEvent) = true
    override fun onPickup(clicked: ItemStack, slot: Int, event: InventoryClickEvent) = true
    override fun onPutin(cursor: ItemStack, slot: Int, event: InventoryClickEvent) = true
    override fun onSwitch(clicked: ItemStack, cursor: ItemStack, slot: Int, event: InventoryClickEvent) = true
    override fun updateItem() {}
}
