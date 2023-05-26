@file:Suppress("UNUSED")

package top.e404.eplugin.hook.worldguard

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import org.bukkit.Location
import org.bukkit.World
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

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

/**
 * 遍历保护区域的xz坐标, 忽略y轴
 */
inline fun ProtectedRegion.forEachXZ(block: (x: Int, z: Int) -> Unit) {
    val min = minimumPoint
    val max = maximumPoint
    for (x in min.blockX..max.blockX) for (z in min.blockZ..max.blockZ) {
        block(x, z)
    }
}

/**
 * 遍历保护区域的坐标
 *
 * @param inner 若设置为true则跳过不包括在region中的方块
 * @param block 操作
 */
inline fun ProtectedRegion.forEach(inner: Boolean = false, block: (x: Int, y: Int, z: Int) -> Unit) {
    val min = minimumPoint
    val max = maximumPoint
    for (x in min.blockX..max.blockX) for (y in min.blockY..max.blockY) {
        for (z in min.blockZ..max.blockZ) {
            if (inner && !contains(x, y, z)) continue
            block(x, y, z)
        }
    }
}
