package top.e404.eplugin.menu.menu

import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.menu.InventoryClickHandler

/**
 * 代表一个菜单
 */
interface InventoryMenu : InventoryClickHandler {
    /**
     * 设置为false以阻止打开菜单的玩家交互自己的背包
     */
    var allowSelf: Boolean

    /**
     * 初始化菜单时调用
     */
    fun onInit() {}

    /**
     * 菜单打开之前调用
     *
     * @param event 事件
     */
    fun onOpen(event: InventoryOpenEvent) {}

    /**
     * 菜单被关闭时调用
     *
     * @param event 事件
     */
    fun onClose(event: InventoryCloseEvent) {}

    /**
     * 玩家打开菜单后点击自己背包, 事件是否取消由[allowSelf]控制
     *
     * @param event 事件
     */
    fun onClickSelfInv(event: InventoryClickEvent) {}

    /**
     * 玩家打开菜单后点击菜单外的区域
     *
     * @param event 事件
     */
    fun onClickBlank(event: InventoryClickEvent) {}

    override fun onPickup(clicked: ItemStack, slot: Int, event: InventoryClickEvent): Boolean

    override fun onPutin(cursor: ItemStack, slot: Int, event: InventoryClickEvent): Boolean

    override fun onSwitch(clicked: ItemStack, cursor: ItemStack, slot: Int, event: InventoryClickEvent): Boolean

    override fun onClick(slot: Int, event: InventoryClickEvent): Boolean

    override fun onHotbarAction(target: ItemStack?, hotbarItem: ItemStack?, slot: Int, hotbar: Int, event: InventoryClickEvent): Boolean

    /**
     * 玩家在菜单中拖动时触发
     *
     * @param event 事件
     * @return 若返回true则取消事件, 返回null代表未处理
     */
    fun onDrag(event: InventoryDragEvent): Boolean

    /**
     * 菜单被主动要求关闭, 通常发生在插件被卸载(关服/热重载)时
     */
    fun onShutdown(player: HumanEntity) {}
}
