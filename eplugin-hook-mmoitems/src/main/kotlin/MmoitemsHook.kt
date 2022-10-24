package top.e404.eplugin.hook.mmoitems

import io.lumine.mythic.lib.api.item.NBTItem
import net.Indyuce.mmoitems.MMOItems
import net.Indyuce.mmoitems.api.Type
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook
import top.e404.eplugin.util.take

@Suppress("UNUSED")
open class MmoitemsHook(
    override val plugin: EPlugin,
) : EHook(plugin, "MMOItems") {
    val mi: MMOItems
        get() = MMOItems.plugin!!

    fun getItem(type: String, id: String) = MMOItems.plugin.getMMOItem(Type.get(type), id)
    fun getNbtItem(itemStack: ItemStack) = NBTItem.get(itemStack)!!
    fun NBTItem.getMiId() = getString("MMOITEMS_ITEM_ID")!!

    fun count(
        inv: Inventory,
        type: String,
        id: String
    ) = inv.count { item ->
        getNbtItem(item).let {
            it.type == type && it.getString("MMOITEMS_ITEM_ID") == id
        }
    }

    fun take(
        inv: Inventory,
        type: String,
        id: String,
        amount: Int
    ) = inv.take(amount) { item ->
        getNbtItem(item).let {
            it.type == type && it.getString("MMOITEMS_ITEM_ID") == id
        }
    }
}