package top.e404.eplugin.hook.worldguard

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import org.bukkit.Location
import org.bukkit.World
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

@Suppress("UNUSED")
open class WorldGuardHook(
    override val plugin: EPlugin,
) : EHook(plugin, "WorldGuard") {
    val rg inline get() = WorldGuardPlugin.inst()!!

    val container inline get() = WorldGuard.getInstance().platform.regionContainer!!

    fun getRegions(location: Location) =
        container.createQuery()
            .getApplicableRegions(BukkitAdapter.adapt(location))

    fun getRegion(location: Location) =
        getRegions(location)
            .maxByOrNull { it.priority }

    fun getRegion(world: World, name: String) =
        container.get(BukkitAdapter.adapt(world))
            ?.getRegion(name)
}
