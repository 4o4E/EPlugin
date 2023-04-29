package top.e404.eplugin.menu.zone

import top.e404.eplugin.menu.InventoryClickHandler
import top.e404.eplugin.menu.getSubZone
import top.e404.eplugin.menu.menu.Menu

/**
 * 代表菜单中的一个区域
 *
 * @property menu 区域所属于的菜单
 * @property x 区域距离菜单左侧边界的格数
 * @property y 区域距离菜单上方边界的格数
 * @property w 区域宽度
 * @property h 区域高度
 */
abstract class MenuZone(
    val menu: Menu,
    val x: Int,
    val y: Int,
    val w: Int,
    val h: Int,
) : InventoryClickHandler {
    // 坐标映射

    /**
     * 内部坐标列表
     */
    val indexes = getSubZone(x, y, w, h)

    /**
     * 坐标翻译表, key是菜单下标, value是区域下标
     */
    val mapMenu2zone = indexes.mapIndexed { zoneIndex, menuIndex -> menuIndex to zoneIndex }.toMap()

    /**
     * 坐标翻译表, key是区域下标, value是菜单下标
     */
    val mapZone2menu = indexes.mapIndexed { zoneIndex, menuIndex -> zoneIndex to menuIndex }.toMap()

    /**
     * 翻译菜单下标到区域下标
     *
     * @param menuIndex 菜单下标
     * @return 区域下标
     */
    fun menu2zone(menuIndex: Int) = mapMenu2zone[menuIndex]

    /**
     * 翻译区域下标到菜单下标
     *
     * @param zoneIndex 区域下标
     * @return 菜单下标
     */
    fun zone2menu(zoneIndex: Int) = mapZone2menu[zoneIndex]

    /**
     * 判断此区域是否包含对应的[slot]
     *
     * @param slot 菜单下标
     */
    operator fun contains(slot: Int) = mapMenu2zone.containsKey(slot)

    /**
     * 更新所有图标
     */
    open fun update() {}

    /**
     * 更新[zoneIndex]处的图标
     *
     * @param zoneIndex 下标
     */
    open fun updateByZoneIndex(zoneIndex: Int) {}

    /**
     * 更新[menuIndex]处的图标
     *
     * @param menuIndex 下标
     */
    open fun updateByMenuIndex(menuIndex: Int) {}
}
