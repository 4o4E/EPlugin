@file:Suppress("UNUSED")

package top.e404.eplugin.menu.menu

import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
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
     * 子区域之外的点击处理, 手动添加时需要自行注入slot的index
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
                exists.boundIndexes.add(index)
                return@forEach
            }
            val slot = handler.invoke(index, char) ?: return@forEach
            existsSlot[char] = slot
            slots[index] = slot
            slot.boundIndexes.add(index)
        }
    }

    /**
     * 更新每个格子的图标
     */
    open fun updateIcon() {
        plugin.debug { "更新菜单中的所有图标" }
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
        plugin.debug { "更新菜单中的图标$index" }
        if (zones.firstOrNull { index in it }?.updateByMenuIndex(index) != null) return
        slots[index]?.let {
            it.updateItem()
            inv.setItem(index, it.item)
            return
        }
        plugin.warn("无法更新下标为${index}的图标")
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
        plugin.debug { "玩家${event.whoClicked.name}交互(pickup)了菜单的第${slot}个格子(clicked: ${clicked.type.name})" }
        return getHandler(slot)?.onPickup(clicked, slot, event) ?: true
    }

    override fun onPutin(cursor: ItemStack, slot: Int, event: InventoryClickEvent): Boolean {
        plugin.debug { "玩家${event.whoClicked.name}交互(putin)了菜单的第${slot}个格子(cursor: ${cursor.type.name})" }
        return getHandler(slot)?.onPutin(cursor, slot, event) ?: true
    }

    override fun onSwitch(clicked: ItemStack, cursor: ItemStack, slot: Int, event: InventoryClickEvent): Boolean {
        plugin.debug {
            "玩家${event.whoClicked.name}交互(switch)了菜单的第${slot}个格子(clicked: ${clicked.type.name}, cursor: ${cursor.type.name})"
        }
        return getHandler(slot)?.onSwitch(clicked, cursor, slot, event) ?: true
    }

    override fun onClick(slot: Int, event: InventoryClickEvent): Boolean {
        plugin.debug { "玩家${event.whoClicked.name}交互(click)了菜单的第${slot}个格子" }
        return getHandler(slot)?.onClick(slot, event) ?: true
    }

    override fun onDrag(event: InventoryDragEvent): Boolean {
        plugin.debug { "玩家${event.whoClicked.name}在菜单中拖动" }
        return true
    }

    override fun onShiftPutin(clicked: ItemStack, event: InventoryClickEvent): Boolean {
        plugin.debug { "玩家${event.whoClicked.name}在菜单中shift+点击移动${event.rawSlot}(${clicked.type.name})" }
        return true
    }

    override fun onHotbarAction(
        target: ItemStack?,
        hotbarItem: ItemStack?,
        slot: Int,
        hotbar: Int,
        event: InventoryClickEvent
    ): Boolean {
        plugin.debug {
            "玩家${event.whoClicked.name}使用快捷键切换${slot}(${target?.type})<->${hotbar}(${hotbarItem?.type})"
        }
        return true
    }

    override fun onClickSelfInv(event: InventoryClickEvent) {
        plugin.debug { "玩家${event.whoClicked.name}在菜单中点击自己背包${event.slot}(${event.currentItem?.type})" }
    }

    override fun onShutdown(player: HumanEntity) {
        plugin.debug { "玩家${player.name}打开的菜单${this::class.java.simpleName}因shutdown而关闭" }
    }
}
