package top.e404.eplugin.hook.mmoitems

import io.lumine.mythic.api.items.ItemManager
import io.lumine.mythic.api.volatilecode.handlers.VolatileItemHandler
import io.lumine.mythic.bukkit.BukkitAPIHelper
import io.lumine.mythic.bukkit.MythicBukkit
import org.bukkit.Location
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook
import top.e404.eplugin.util.take
import kotlin.jvm.optionals.getOrNull

@Suppress("UNUSED")
open class MythicMobsHook(
    override val plugin: EPlugin,
) : EHook(plugin, "MythicMobs") {
    val mm: MythicBukkit
        get() = MythicBukkit.inst()

    val itemManager: ItemManager
        get() = mm.itemManager

    val itemHandler: VolatileItemHandler
        get() = mm.volatileCodeHandler.itemHandler

    val apiHelper: BukkitAPIHelper
        get() = mm.apiHelper

    @ExperimentalStdlibApi
    fun getItem(name: String) = itemManager.getItem(name).getOrNull()

    fun getMmId(item: ItemStack) = itemHandler.getNBTData(item).let { tag ->
        if (tag.containsKey("MYTHIC_TYPE")) tag.getString("MYTHIC_TYPE")
        else null
    }

    fun getMythicMob(type: String) = apiHelper.getMythicMob(type)

    fun spawnMythicMob(type: String, location: Location) = apiHelper.spawnMythicMob(type, location)

    fun count(
        inv: Inventory,
        name: String
    ) = inv.count { item ->
        getMmId(item) == name
    }

    fun take(
        inv: Inventory,
        name: String,
        amount: Int
    ) = inv.take(amount) { item ->
        getMmId(item) == name
    }
}