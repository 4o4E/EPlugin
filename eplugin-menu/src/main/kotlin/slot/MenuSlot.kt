@file:Suppress("UNUSED")

package top.e404.eplugin.menu.slot

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.menu.InventoryClickHandler
import top.e404.eplugin.menu.menu.Menu
import top.e404.eplugin.util.emptyItem

/**
 * 代表一个格子, 抽象对此格子的点击处理
 *
 * @property menu 属于的菜单
 */
abstract class MenuSlot(
    open val menu: Menu
) : InventoryClickHandler {
    /**
     * 对应在菜单中展示的物品
     */
    open var item = emptyItem

    /**
     * 更新物品
     */
    abstract fun updateItem()

    abstract override fun onClick(slot: Int, event: InventoryClickEvent): Boolean?
    abstract override fun onPickup(clicked: ItemStack, slot: Int, event: InventoryClickEvent): Boolean?
    abstract override fun onPutin(cursor: ItemStack, slot: Int, event: InventoryClickEvent): Boolean?
    abstract override fun onSwitch(
        clicked: ItemStack,
        cursor: ItemStack,
        slot: Int,
        event: InventoryClickEvent
    ): Boolean?
}