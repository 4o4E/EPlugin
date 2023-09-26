@file:Suppress("UNUSED")

package top.e404.eplugin.menu

import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.*
import top.e404.eplugin.EPlugin
import top.e404.eplugin.listener.EListener
import top.e404.eplugin.menu.menu.InventoryMenu

/**
 * 菜单管理器, 负责监听事件, 处理玩家打开/点击/关闭菜单
 *
 * @property plugin 属于的插件
 */
open class EMenuManager(override val plugin: EPlugin) : EListener(plugin) {
    // 正在处理中的菜单<玩家, 使用中的菜单>
    val menus = HashMap<HumanEntity, InventoryMenu>()

    /**
     * 给玩家打开菜单, 只有通过此方法打开的菜单才能接入到
     *
     * @param menu 菜单
     * @param player 玩家
     */
    fun openMenu(menu: InventoryMenu, player: HumanEntity) {
        // 先关闭已打开的菜单, 确保正确触发 InventoryCloseEvent
        player.closeInventory()
        // 打开新菜单
        menus[player] = menu
        menu.onInit()
        plugin.debug { "为玩家${player.name}打开菜单${menu::class.java.simpleName}" }
        player.openInventory(menu.inv)
    }

    /**
     * 若玩家打开了管理器管理的菜单, 则关闭菜单
     *
     * @param p 玩家
     */
    fun closeMenu(p: HumanEntity) {
        val menu = menus.remove(p)
        if (menu != null) {
            plugin.debug { "为玩家${p.name}关闭菜单${menu::class.java.simpleName}" }
            p.closeInventory()
        }
    }

    /**
     * 插件卸载时应调用此指令关闭所有已打开的菜单
     */
    open fun shutdown() {
        plugin.debug { "菜单管理器shutdown" }
        val iterator = menus.entries.iterator()
        while (iterator.hasNext()) {
            val (player, menu) = iterator.next()
            menu.onShutdown(player)
            player.closeInventory()
            iterator.remove()
        }
    }

    // 打开菜单
    @EventHandler(ignoreCancelled = true)
    fun InventoryOpenEvent.onEvent() {
        menus[player]?.let {
            plugin.debug { "玩家${player.name}打开菜单${it::class.java.simpleName}" }
            it.onOpen(this)
        }
    }

    // 关闭菜单
    @EventHandler(ignoreCancelled = true)
    fun InventoryCloseEvent.onEvent() {
        menus.remove(player)?.let {
            plugin.debug { "玩家${player.name}打开菜单${it::class.java.simpleName}" }
            it.onClose(this)
        }
    }

    private val shift = listOf(InventoryAction.PICKUP_ALL)

    // 交互菜单
    @EventHandler(ignoreCancelled = true)
    fun InventoryClickEvent.onEvent() {
        val menu = menus[whoClicked] ?: return
        try {
            val cursor = cursor
            val clicked = currentItem
            if (isShiftClick // shift点击
                && clicked != null // 点击的格子中有物品
                && menu.inv.firstEmpty() != -1 // 菜单种有空位
                && clickedInventory == whoClicked.openInventory.bottomInventory // 点的是自己背包
            ) {
                if (menu.onShiftPutin(clicked, this)) isCancelled = true
                return
            }
            // 打开菜单后点击了自己背包
            if (clickedInventory != menu.inv) {
                if (!menu.allowSelf) isCancelled = true
                menu.onClickSelfInv(this)
                return
            }
            // 点击了菜单外的区域
            if (slot == -999) {
                menu.onClickBlank(this)
                return
            }
            val cursorNotNull = cursor != null && cursor.type != Material.AIR
            val clickedNotNull = clicked != null && clicked.type != Material.AIR
            if (hotbarButton != -1) {
                val hotbarItem = whoClicked.inventory.getItem(hotbarButton)
                isCancelled = menu.onHotbarAction(clicked, hotbarItem, slot, hotbarButton, this)
                return
            }
            isCancelled = when {
                cursorNotNull && clickedNotNull -> menu.onSwitch(clicked!!, cursor!!, slot, this)
                !cursorNotNull && clickedNotNull -> menu.onPickup(clicked!!, slot, this)
                cursorNotNull && !clickedNotNull -> menu.onPutin(cursor!!, slot, this)
                else -> menu.onClick(slot, this)
            }
        } catch (t: Throwable) {
            plugin.warn("处理菜单${menu::class.java.name}点击时出现异常, 已阻止点击", t)
            isCancelled = true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun InventoryDragEvent.onEvent() {
        val menu = menus[whoClicked] ?: return
        try {
            if (menu.onDrag(this)) isCancelled = true
        } catch (t: Throwable) {
            plugin.warn("处理菜单${menu::class.java.name}拖动时出现异常, 已阻止", t)
            isCancelled = true
        }
    }
}
