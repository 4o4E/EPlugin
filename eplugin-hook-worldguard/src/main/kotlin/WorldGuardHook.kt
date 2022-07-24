package top.e404.eplugin.hook.worldguard

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import com.sk89q.worldguard.protection.regions.RegionContainer
import org.bukkit.Location
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook


open class WorldGuardHook(
    override val plugin: EPlugin,
) : EHook<WorldGuardPlugin>(plugin, "WorldGuard") {
    val rg: WorldGuardPlugin
        get() = WorldGuardPlugin.inst()!!

    val container: RegionContainer
        get() = WorldGuard.getInstance().platform.regionContainer

    fun getRegion(location: Location) =
        container.createQuery()
            .getApplicableRegions(BukkitAdapter.adapt(location))
            .maxByOrNull { it.priority }
}