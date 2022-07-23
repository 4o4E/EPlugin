package top.e404.eplugin.hook.worldguard

import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import com.sk89q.worldguard.protection.regions.RegionContainer
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

class WorldGuardHook(
    override val plugin: EPlugin,
) : EHook<WorldGuardPlugin>(plugin, "WorldGuard") {
    val bbox: WorldGuardPlugin
        get() = WorldGuardPlugin.inst()!!

    val container: RegionContainer
        get() = WorldGuard.getInstance().platform.regionContainer
}