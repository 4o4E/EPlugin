package top.e404.eplugin.hook.mmoitems

import io.lumine.mythic.lib.api.item.NBTItem
import net.Indyuce.mmoitems.MMOItems
import net.Indyuce.mmoitems.api.Type
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook
import top.e404.eplugin.util.take

@Suppress("UNUSED")
open class MmoitemsHook(
    override val plugin: EPlugin,
) : EHook(plugin, "MMOItems") {
    val mi inline get() = MMOItems.plugin!!

    fun getMmoItem(type: String, id: String) = mi.getMMOItem(Type.get(type), id)
    fun getItem(type: String, id: String) = mi.getMMOItem(Type.get(type), id)?.newBuilder()?.build()
    fun getNbtItem(itemStack: ItemStack) = NBTItem.get(itemStack)!!
    fun getId(itemStack: ItemStack): String = getNbtItem(itemStack).id
    fun getType(itemStack: ItemStack): String? = getNbtItem(itemStack).type
    fun getTypeId(itemStack: ItemStack) = getNbtItem(itemStack).typeId

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

/**
 * 需要判断是否为""
 */
inline val NBTItem.id get() = getString("MMOITEMS_ITEM_ID")!!
inline val NBTItem.idOrNull get() = id.let { if (it == "") null else it }
val NBTItem.typeId: String?
    get() {
        val type = type ?: return null
        val id = idOrNull ?: return null
        return "$type:$id"
    }

val MMOItem.typeId get() = "${type.id}:$id"

fun NBTItem.equals(type: String, id: String) = id == this.id && type == this.type
