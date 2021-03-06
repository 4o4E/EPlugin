@file:Suppress("UNUSED")

package top.e404.eplugin.menu

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import top.e404.eplugin.EPlugin
import top.e404.eplugin.listener.EListener
import top.e404.eplugin.menu.menu.Menu

/**
 * 菜单管理器, 负责监听事件, 处理玩家打开/点击/关闭菜单
 *
 * @property plugin 属于的插件
 */
open class PluginMenuManager(override val plugin: EPlugin) : EListener(plugin) {
    // 正在处理中的菜单<玩家, 使用中的菜单>
    val menus = HashMap<Player, Menu>()

    /**
     * 给玩家打开菜单, 只有通过此方法打开的菜单才能接入到
     *
     * @param m 菜单
     * @param p 玩家
     */
    fun openMenu(m: Menu, p: Player) {
        // 先关闭已打开的菜单, 确保正确触发 InventoryCloseEvent
        p.closeInventory()
        // 打开新菜单
        menus[p] = m
        m.onInit()
        p.openInventory(m.inv)
    }

    // 打开菜单
    @EventHandler
    fun InventoryOpenEvent.onEvent() {
        menus[player]?.onOpen(this)
    }

    // 关闭菜单
    @EventHandler
    fun InventoryCloseEvent.onEvent() {
        menus.remove(player)?.onClose(this)
    }

    // 交互菜单
    @EventHandler
    fun InventoryClickEvent.onEvent() {
        val menu = menus[whoClicked] ?: return
        val cursor = cursor
        val clicked = currentItem
        // 打开菜单后点击了自己背包
        if (clickedInventory != menu.inv) {
            if (!menu.self) isCancelled = true
            menu.onClickSelfInv(this)
            return
        }
        // 点击了菜单外的区域
        if (slot == -999) {
            menu.onClickBlank(this)
            return
        }
        val b = when {
            cursor != null && clicked != null -> menu.onSwitch(clicked, cursor, slot, this)
            cursor == null && clicked != null -> menu.onPickup(clicked, slot, this)
            cursor != null && clicked == null -> menu.onPutin(cursor, slot, this)
            else -> menu.onClick(slot, this)
        }
        if (b) isCancelled = true
    }
}