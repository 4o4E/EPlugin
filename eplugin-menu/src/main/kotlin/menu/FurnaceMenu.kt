package top.e404.eplugin.menu.menu

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.FurnaceInventory
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.EPlugin

/**
 * 代表一个熔炉菜单
 *
 * @property plugin 注册菜单的插件
 * @property title 菜单标题
 */
open class FurnaceMenu(
    val plugin: EPlugin,
    val title: String,
    override var allowSelf: Boolean = false
) : InventoryMenu {
    override val inv = Bukkit.createInventory(null, InventoryType.FURNACE, title) as FurnaceInventory

    override fun onPickup(clicked: ItemStack, slot: Int, event: InventoryClickEvent): Boolean {
        plugin.debug {
            plugin.langManager[
                    "debug.menu.on_pickup",
                    "slot" to slot,
                    "clicked" to clicked.type.name,
                    "player" to event.whoClicked.name,
            ]
        }
        return true
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
        return true
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
        return true
    }

    override fun onClick(slot: Int, event: InventoryClickEvent): Boolean {
        plugin.debug {
            plugin.langManager[
                    "debug.menu.on_click",
                    "slot" to slot,
                    "player" to event.whoClicked.name,
            ]
        }
        return true
    }

    override fun onHotbarAction(target: ItemStack?, hotbarItem: ItemStack?, slot: Int, hotbar: Int, event: InventoryClickEvent): Boolean{
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
