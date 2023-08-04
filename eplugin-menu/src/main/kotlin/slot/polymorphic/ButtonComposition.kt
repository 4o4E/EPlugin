package top.e404.eplugin.menu.slot.polymorphic

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.menu.slot.MenuSlot

/**
 * 组成多态按钮的一种状态
 *
 * @property slot 对应的菜单槽位
 * @property order 处理优先级, 计算时会先按照优先级排序, 然后再通过[condition]计算是否该显示
 */
abstract class ButtonComposition(val order: Int = 0) {
    lateinit var slot: MenuSlot
    protected inline val menu get() = slot.menu

    /**
     * 判断该按钮是否该显示
     *
     * @return 若该显示则返回true
     */
    abstract fun condition(): Boolean

    /**
     * 获取按钮物品
     */
    abstract val item: ItemStack

    /**
     * 处理点击事件
     *
     * @param slot 对应的菜单下标
     * @param event 对于的点击事件
     * @return 若取消则返回true
     */
    abstract fun onClick(slot: Int, event: InventoryClickEvent): Boolean
}