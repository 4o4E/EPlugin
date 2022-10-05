package top.e404.eplugin.hook.mmoitems

import io.lumine.mythic.lib.api.item.NBTItem
import net.Indyuce.mmoitems.MMOItems
import net.Indyuce.mmoitems.api.Type
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

@Suppress("UNUSED")
open class MmoitemsHook(
    override val plugin: EPlugin,
) : EHook<MMOItems>(plugin, "MMOItems") {
    val mi: MMOItems
        get() = MMOItems.plugin!!

    fun getItem(type: String, id: String) = MMOItems.plugin.getMMOItem(Type.get(type), id)
    fun getNbtItem(itemStack: ItemStack) = NBTItem.get(itemStack)!!
    fun NBTItem.getMiId() = getString("MMOITEMS_ITEM_ID")!!
}