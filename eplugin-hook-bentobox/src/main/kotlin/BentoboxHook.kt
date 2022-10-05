package top.e404.eplugin.hook.bentobox

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook
import world.bentobox.bentobox.BentoBox
import world.bentobox.bentobox.database.objects.Island

@Suppress("UNUSED")
open class BentoboxHook(
    override val plugin: EPlugin,
) : EHook<BentoBox>(plugin, "BentoBox") {
    val bbox: BentoBox
        get() = BentoBox.getInstance()!!

    fun getUser(p: Player) = bbox.playersManager.getUser(p.uniqueId)
    fun getUser(name: String) = bbox.playersManager.getUser(name)

    fun getIsland(l: Location): Island? = bbox.islandsManager.getIslandAt(l).orElse(null)
    fun getIsland(block: Block) = getIsland(block.location)
    fun getIsland(p: Player) = bbox.playersManager
        .getUser(p.uniqueId)
        ?.let { bbox.islandsManager.getIsland(p.world, it) }
}