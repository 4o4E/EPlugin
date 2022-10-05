package top.e404.eplugin.hook.worldedit

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.WorldEditPlugin
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

open class WorldEditHook(
    override val plugin: EPlugin,
) : EHook<WorldEditPlugin>(plugin, "WorldEdit") {
    val we: WorldEdit
        get() = WorldEdit.getInstance()

}