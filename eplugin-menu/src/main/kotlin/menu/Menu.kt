package top.e404.eplugin.menu.menu

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.EPlugin
import top.e404.eplugin.menu.InventoryClickHandler
import top.e404.eplugin.menu.slot.MenuSlot
import top.e404.eplugin.menu.zone.MenuZone

/**
 * 代表一个菜单
 *
 * @property plugin 注册菜单的插件
 * @property row 菜单行数
 * @property title 菜单标题
 * @property self 是否允许打开菜单的玩家交互自己的背包
 */
open class Menu(
    val plugin: EPlugin,
    val row: Int,
    val title: String,
    var self: Boolean = false
) : InventoryClickHandler {
    /**
     * 菜单对应的背包
     */
    val inv = Bukkit.createInventory(null, row.coerceIn(1, 6) * 9, title)

    /**
     * 菜单中的子区域
     */
    val zones: MutableSet<MenuZone> = mutableSetOf()

    /**
     * 子区域之外的点击处理
     */
    val slots: MutableMap<Int, MenuSlot> = mutableMapOf()

    /**
     * 更新每个格子的图标
     */
    open fun updateIcon() {
        // 更新zone
        zones.forEach { it.update() }
        // 更新slot
        slots.forEach { (index, slot) ->
            slot.updateItem()
            inv.setItem(index, slot.item)
        }
    }

    /**
     * 刷新[index]处的图标
     *
     * @param index 下标
     */
    open fun updateIcon(index: Int) {
        zones.firstOrNull { index in it }?.updateByMenuIndex(index)
    }

    /**
     * 菜单初始化, 手动调用
     */
    open fun onInit() {
        updateIcon()
    }

    /**
     * 菜单打开之前调用
     *
     * @param event 事件
     */
    open fun onOpen(event: InventoryOpenEvent) {
    }

    /**
     * 菜单被关闭时调用
     *
     * @param event 事件
     */
    open fun onClose(event: InventoryCloseEvent) {
    }

    /**
     * 玩家打开菜单后点击自己背包, 事件是否取消由[self]控制
     *
     * @param event 事件
     */
    open fun onClickSelfInv(event: InventoryClickEvent) {
    }

    /**
     * 玩家打开菜单后点击菜单外的区域
     *
     * @param event 事件
     */
    open fun onClickBlank(event: InventoryClickEvent) {
    }

    fun getHandler(slot: Int): InventoryClickHandler? {
        for (zone in zones) if (slot in zone) return zone
        return slots[slot]
    }

    override fun onPickup(clicked: ItemStack, slot: Int, event: InventoryClickEvent): Boolean {
        plugin.debug {
            plugin.langManager[
                    "debug.menu.on_pickup",
                    "slot" to slot,
                    "clicked" to clicked.type.name,
                    "player" to event.whoClicked.name,
            ]
        }
        return getHandler(slot)?.onPickup(clicked, slot, event) ?: true
    }

    override fun onPutin(cursor: ItemStack, slot: Int, event: InventoryClickEvent): Boolean {
        plugin.debug {
            plugin.langManager[
                    "debug.menu.on_putin",
                    "slot" to slot,
                    "cursor" to cursor.type.name,
                    "player" to event.whoClicked.name,
            ]
        }
        return getHandler(slot)?.onPutin(cursor, slot, event) ?: true
    }

    override fun onSwitch(clicked: ItemStack, cursor: ItemStack, slot: Int, event: InventoryClickEvent): Boolean {
        plugin.debug {
            plugin.langManager[
                    "debug.menu.on_switch",
                    "slot" to slot,
                    "clicked" to clicked.type.name,
                    "cursor" to cursor.type.name,
                    "player" to event.whoClicked.name,
            ]
        }
        return getHandler(slot)?.onSwitch(clicked, cursor, slot, event) ?: true
    }

    override fun onClick(slot: Int, event: InventoryClickEvent): Boolean {
        plugin.debug {
            plugin.langManager[
                    "debug.menu.on_click",
                    "slot" to slot,
                    "player" to event.whoClicked.name,
            ]
        }
        return getHandler(slot)?.onClick(slot, event) ?: true
    }
}
