@file:Suppress("UNUSED")

package top.e404.eplugin.menu.menu

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.EPlugin
import top.e404.eplugin.menu.InventoryClickHandler
import top.e404.eplugin.menu.slot.MenuSlot
import top.e404.eplugin.menu.zone.MenuZone

/**
 * 代表一个箱子菜单
 *
 * @property plugin 注册菜单的插件
 * @property row 菜单行数
 * @property title 菜单标题
 */
open class ChestMenu(
    val plugin: EPlugin,
    val row: Int,
    val title: String,
    override var allowSelf: Boolean = false
) : InventoryMenu {
    /**
     * 菜单对应的背包
     */
    override val inv = Bukkit.createInventory(null, row.coerceIn(1, 6) * 9, title)

    /**
     * 菜单中的子区域
     */
    val zones: MutableSet<MenuZone> = mutableSetOf()

    /**
     * 子区域之外的点击处理
     */
    val slots: MutableMap<Int, MenuSlot> = mutableMapOf()

    /**
     * 快捷初始化slot
     *
     * @param list 模板
     * @param handler 创建slot
     */
    fun initSlots(list: List<String>, handler: (Int, Char) -> MenuSlot?) {
        require(list.size == row)
        list.forEach { require(it.length == 9) }
        val existsSlot = mutableMapOf<Char, MenuSlot>()
        (0 until list.size * 9).forEach { index ->
            val char = list[index / 9][index % 9]
            val exists = existsSlot[char]
            if (exists != null) {
                slots[index] = exists
                return@forEach
            }
            val slot = handler.invoke(index, char) ?: return@forEach
            existsSlot[char] = slot
            slots[index] = slot
        }
    }

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
    override fun onInit() = updateIcon()

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

    override fun onHotbarAction(target: ItemStack?, hotbarItem: ItemStack?, slot: Int, hotbar: Int, event: InventoryClickEvent): Boolean {
        plugin.debug {
            plugin.langManager[
                "debug.menu.on_hotbar",
                "slot" to slot,
                "slot_item" to target?.type,
                "hotbar" to hotbar,
                "hotbar_item" to hotbarItem?.type,
                "player" to event.whoClicked.name,
            ]
        }
        return true
    }
}
