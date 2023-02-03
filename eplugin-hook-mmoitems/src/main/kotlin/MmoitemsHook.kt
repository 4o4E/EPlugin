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

    fun getMmoItem(type: String, id: String) = mi.getMMOItem(Type.get(type), id)
    fun getItem(type: String, id: String) = mi.getMMOItem(Type.get(type), id)?.newBuilder()?.build(false)
    fun getNbtItem(itemStack: ItemStack) = NBTItem.get(itemStack)!!
    val NBTItem.id: String?
        get() = getString("MMOITEMS_ITEM_ID")

    fun getId(itemStack: ItemStack) = getNbtItem(itemStack).id
    fun getType(itemStack: ItemStack) = getNbtItem(itemStack).type
    fun NBTItem.equals(type: String, id: String) = id == this.id && type == this.type

    fun count(
        inv: Inventory,
        type: String,
        id: String,
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