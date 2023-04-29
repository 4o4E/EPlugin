package top.e404.eplugin.menu

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * 代表一个处理菜单点击事件的对象
 */
interface InventoryClickHandler {
    /**
     * 玩家空手点击空格子
     *
     * @param slot 点击的格子
     * @param event 事件
     * @return 若返回true则取消事件, 返回null代表未处理
     */
    fun onClick(
        slot: Int,
        event: InventoryClickEvent
    ): Boolean?

    /**
     * 玩家空手点击菜单中的物品
     *
     * @param clicked 点击的物品
     * @param slot 点击的格子
     * @param event 事件
     * @return 若返回true则取消事件, 返回null代表未处理
     */
    fun onPickup(
        clicked: ItemStack,
        slot: Int,
        event: InventoryClickEvent
    ): Boolean?

    /**
     * 玩家拿着物品点击空菜单格子
     *
     * @param cursor 光标上的物品
     * @param slot 点击的格子
     * @param event 事件
     * @return 若返回true则取消事件, 返回null代表未处理
     */
    fun onPutin(
        cursor: ItemStack,
        slot: Int,
        event: InventoryClickEvent
    ): Boolean?

    /**
     * 玩家拿着物品点击菜单上的物品
     *
     * @param clicked 点击的物品
     * @param cursor 光标上的物品
     * @param slot 点击的格子
     * @param event 事件
     * @return 若返回true则取消事件, 返回null代表未处理
     */
    fun onSwitch(
        clicked: ItemStack,
        cursor: ItemStack,
        slot: Int,
        event: InventoryClickEvent
    ): Boolean?
}
