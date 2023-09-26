@file:Suppress("UNUSED")

package top.e404.eplugin.menu.menu

import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.FurnaceInventory
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.EPlugin

/**
 * 代表一个熔炉菜单
 *
 * @property plugin 注册菜单的插件
 * @property title 菜单标题
 */
open class FurnaceMenu(
    val plugin: EPlugin,
    val title: String,
    override var allowSelf: Boolean = false
) : InventoryMenu {
    override val inv = Bukkit.createInventory(null, InventoryType.FURNACE, title) as FurnaceInventory

    override fun onPickup(clicked: ItemStack, slot: Int, event: InventoryClickEvent): Boolean {
        plugin.debug { "玩家${event.whoClicked.name}交互(pickup)了菜单的第${slot}个格子(clicked: ${clicked.type.name})" }
        return true
    }

    override fun onPutin(cursor: ItemStack, slot: Int, event: InventoryClickEvent): Boolean {
        plugin.debug { "玩家${event.whoClicked.name}交互(putin)了菜单的第${slot}个格子(cursor: ${cursor.type.name})" }
        return true
    }

    override fun onSwitch(clicked: ItemStack, cursor: ItemStack, slot: Int, event: InventoryClickEvent): Boolean {
        plugin.debug {
            "玩家${event.whoClicked.name}交互(switch)了菜单的第${slot}个格子(clicked: ${clicked.type.name}, cursor: ${cursor.type.name})"
        }
        return true
    }

    override fun onClick(slot: Int, event: InventoryClickEvent): Boolean {
        plugin.debug { "玩家${event.whoClicked.name}交互(click)了菜单的第${slot}个格子" }
        return true
    }

    override fun onHotbarAction(target: ItemStack?, hotbarItem: ItemStack?, slot: Int, hotbar: Int, event: InventoryClickEvent): Boolean{
        plugin.debug {
            "玩家${event.whoClicked.name}使用快捷键切换${slot}(${target?.type})<->${hotbar}(${hotbarItem?.type})"
        }
        return true
    }

    override fun onShiftPutin(clicked: ItemStack, event: InventoryClickEvent): Boolean {
        plugin.debug { "玩家${event.whoClicked.name}在菜单中shift+点击移动${event.rawSlot}(${clicked.type.name})" }
        return true
    }

    override fun onDrag(event: InventoryDragEvent): Boolean {
        plugin.debug { "玩家${event.whoClicked.name}在菜单中拖动" }
        return true
    }

    override fun onClickSelfInv(event: InventoryClickEvent) {
        plugin.debug { "玩家${event.whoClicked.name}在菜单中点击自己背包${event.slot}(${event.currentItem?.type})" }
    }

    override fun onShutdown(player: HumanEntity) {
        plugin.debug { "玩家${player.name}打开的菜单${this::class.java.simpleName}因shutdown而关闭" }
    }
}
