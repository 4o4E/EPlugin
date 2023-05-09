package top.e404.eplugin.hook.mmoitems

import io.lumine.mythic.api.items.ItemManager
import io.lumine.mythic.api.mobs.GenericCaster
import io.lumine.mythic.api.volatilecode.handlers.VolatileItemHandler
import io.lumine.mythic.bukkit.BukkitAPIHelper
import io.lumine.mythic.bukkit.BukkitAdapter
import io.lumine.mythic.bukkit.MythicBukkit
import io.lumine.mythic.core.skills.SkillMetadataImpl
import io.lumine.mythic.core.skills.SkillTriggers
import io.lumine.mythic.core.skills.variables.Variable
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook
import top.e404.eplugin.util.take

@Suppress("UNUSED")
open class MythicMobsHook(
    override val plugin: EPlugin,
) : EHook(plugin, "MythicMobs") {
    val mm inline get() = MythicBukkit.inst()

    val itemManager: ItemManager
        get() = mm.itemManager

    val itemHandler: VolatileItemHandler
        get() = mm.volatileCodeHandler.itemHandler

    val apiHelper: BukkitAPIHelper
        get() = mm.apiHelper

    fun getItem(name: String) = itemManager.getItem(name).let { if (it.isPresent) it.get() else null }

    fun getMmId(item: ItemStack) = itemHandler.getNBTData(item).let { tag ->
        if (tag.containsKey("MYTHIC_TYPE")) tag.getString("MYTHIC_TYPE")
        else null
    }

    fun getMythicMob(type: String) = apiHelper.getMythicMob(type)

    fun getMythicMobByEntity(entity: Entity) = apiHelper.getMythicMobInstance(entity)

    fun spawnMythicMob(type: String, location: Location) = apiHelper.spawnMythicMob(type, location)

    fun castSkill(
        entity: Entity,
        skillName: String,
        vararg variables: Pair<String, Variable>
    ) {
        val skill = MythicBukkit.inst().skillManager.getSkill(skillName).get()
        val adaptedEntity = BukkitAdapter.adapt(entity)
        val adaptedLocation = BukkitAdapter.adapt(entity.location)
        val caster =
            if (MythicBukkit.inst().mobManager.isActiveMob(entity.uniqueId)) MythicBukkit.inst().mobManager.getMythicMobInstance(entity)
            else GenericCaster(adaptedEntity)
        val data = SkillMetadataImpl(
            SkillTriggers.API,
            caster,
            adaptedEntity,
            adaptedLocation,
            listOf(adaptedEntity),
            listOf(adaptedLocation),
            1F
        )
        variables.forEach { (key, variable) -> data.variables.put(key, variable) }
        if (skill.isUsable(data, SkillTriggers.API)) skill.execute(data)
    }

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
