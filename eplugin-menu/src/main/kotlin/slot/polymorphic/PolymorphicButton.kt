@file:Suppress("UNUSED")

package top.e404.eplugin.menu.slot.polymorphic

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.menu.menu.ChestMenu
import top.e404.eplugin.menu.slot.MenuButton

/**
 * 代表一个按钮, 能自动管理多种状态
 *
 * @property menu 持有此格子的菜单
 * @param compositions 所有的按钮状态
 */
abstract class PolymorphicButton(
    override var menu: ChestMenu,
    compositions: Collection<ButtonComposition>
) : MenuButton(menu) {
    /**
     * 该多态按钮对应的所有状态
     */
    protected val compositions = compositions.sortedByDescending(ButtonComposition::order).onEach { it.slot = this }

    /**
     * 当前的状态, 若没有匹配的状态则返回[defaultItem]并由[defaultOnClick]处理点击
     */
    var current: ButtonComposition? = compositions.firstOrNull(ButtonComposition::condition)
        protected set

    /**
     * 更新状态和物品, 点击该按钮时自动触发, 也可以手动触发
     */
    fun updateState() {
        // 计算当前按钮
        current = compositions.firstOrNull(ButtonComposition::condition)
        updateItem()
        boundIndexes.forEach(menu::updateIcon)
    }

    override fun updateItem() {
        super.updateItem()
        // 更新图标
        item = current?.item ?: defaultItem
    }

    override fun onClick(slot: Int, event: InventoryClickEvent): Boolean {
        val result = current?.onClick(slot, event) ?: defaultOnClick(slot, event)
        // 点击后更新状态
        updateState()
        return result
    }

    /**
     * 默认显示的物品, 当所有composition都不匹配时显示该物品
     */
    abstract val defaultItem: ItemStack

    /**
     * 默认点击的处理, 当所有composition都不匹配时使用该方法处理点击
     *
     * @param slot 对应的菜单下标
     * @param event 对于的点击事件
     * @return 若取消则返回true
     */
    abstract fun defaultOnClick(slot: Int, event: InventoryClickEvent): Boolean
}
