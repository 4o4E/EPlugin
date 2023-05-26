package top.e404.eplugin.menu

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * 代表一个处理菜单点击事件的对象
 */
interface InventoryClickHandler {
    /**
     * 对应的[Inventory]
     */
    val inv: Inventory

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

    /**
     * 玩家通过1-9的快捷键交换菜单中物品
     *
     * @param target 光标指着的物品
     * @param hotbarItem 通过快捷键确定的槽位中的物品
     * @param slot 光标指着的物品的下标
     * @param hotbar 快捷键下标
     * @param event 事件
     * @return 若返回true则取消事件, 返回null代表未处理
     */
    fun onHotbarAction(
        target: ItemStack?,
        hotbarItem: ItemStack?,
        slot: Int,
        hotbar: Int,
        event: InventoryClickEvent
    ): Boolean?
}
