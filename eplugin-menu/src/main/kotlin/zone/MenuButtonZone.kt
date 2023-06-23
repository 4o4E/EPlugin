package top.e404.eplugin.menu.zone

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.menu.Displayable
import top.e404.eplugin.menu.menu.ChestMenu
import top.e404.eplugin.util.emptyItem
import top.e404.eplugin.util.getPageCount
import top.e404.eplugin.util.splitByPage

/**
 * 代表菜单中的一个区域
 *
 * @property menu 区域所属于的菜单
 * @property x 区域距离菜单左侧边界的格数
 * @property y 区域距离菜单上方边界的格数
 * @property w 区域宽度
 * @property h 区域高度
 */
abstract class MenuButtonZone<T : Displayable>(
    override val menu: ChestMenu,
    x: Int,
    y: Int,
    w: Int,
    h: Int,
    open val data: MutableList<T>
) : MenuZone(menu, x, y, w, h) {

    // 分页

    /**
     * 分页大小
     */
    val pageSize = w * h

    /**
     * 当前页数
     */
    var page = 0

    /**
     * 最大页数
     */
    val maxPage
        get() = getPageCount(data.size, pageSize)

    /**
     * 判断此区域是否有上一页
     */
    val hasPrev: Boolean
        get() = page != 0

    /**
     * 判断此区域是否有下一页
     */
    val hasNext: Boolean
        get() = page < maxPage - 1

    /**
     * 翻到上一页(若存在)
     */
    fun prevPage() {
        if (!hasPrev) return
        page--
        update()
    }

    /**
     * 翻到下一页(若存在)
     */
    fun nextPage() {
        if (!hasNext) return
        page++
        update()
    }

    // 图标

    override fun update() {
        val byPage = data.splitByPage(pageSize, page)
        for (i in 0 until pageSize) {
            val displayable = byPage.getOrNull(i)
            // 不在列表中的设置为空
            if (displayable == null) {
                menu.inv.setItem(zone2menu(i)!!, emptyItem)
                continue
            }
            // 更新图标
            displayable.update()
            // 更新菜单物品
            menu.inv.setItem(zone2menu(i)!!, displayable.item)
        }
    }

    /**
     * 更新[zoneIndex]处的图标
     *
     * @param zoneIndex 下标
     */
    override fun updateByZoneIndex(zoneIndex: Int) {
        val byPage = data.splitByPage(pageSize, page)
        val displayable = byPage[zoneIndex]
        displayable.update()
        menu.inv.setItem(zone2menu(zoneIndex)!!, displayable.item)
    }

    /**
     * 更新[menuIndex]处的图标
     *
     * @param menuIndex 下标
     */
    override fun updateByMenuIndex(menuIndex: Int) {
        val zoneIndex = menu2zone(menuIndex) ?: return
        updateByZoneIndex(zoneIndex)
    }

    /**
     * 处理zone的点击
     *
     * @param menuIndex 点击的格子在菜单中的index
     * @param zoneIndex 点击的格子在zone中的index
     * @param itemIndex 点击的格子对应的item在data中的index
     * @param event 事件
     * @return 若返回true则取消事件, 返回null代表未处理
     */
    abstract fun onClick(menuIndex: Int, zoneIndex: Int, itemIndex: Int, event: InventoryClickEvent): Boolean?

    override fun onClick(slot: Int, event: InventoryClickEvent): Boolean? {
        val zoneIndex = menu2zone(slot)!!
        val dataIndex = zoneIndex + page * pageSize
        return onClick(slot, zoneIndex, dataIndex, event)
    }

    override fun onPickup(clicked: ItemStack, slot: Int, event: InventoryClickEvent) = onClick(slot, event)
    override fun onPutin(cursor: ItemStack, slot: Int, event: InventoryClickEvent) = onClick(slot, event)
    override fun onSwitch(clicked: ItemStack, cursor: ItemStack, slot: Int, event: InventoryClickEvent) = onClick(slot, event)
    override fun onHotbarAction(target: ItemStack?, hotbarItem: ItemStack?, slot: Int, hotbar: Int, event: InventoryClickEvent) = onClick(slot, event)
}
