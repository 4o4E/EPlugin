package top.e404.eplugin.hook.mythiclib

import io.lumine.mythic.lib.MythicLib
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

@Suppress("UNUSED")
open class MythicLibHook(
    override val plugin: EPlugin,
) : EHook(plugin, "MythicLib") {
    val pl inline get() = MythicLib.plugin!!
    inline val modifierManager get() = pl.modifiers!!
    inline val damageManager get() = pl.damage!!
    inline val entityManager get() = pl.entities!!
    inline val statManager get() = pl.stats!!
    inline val elementManager get() = pl.elements!!
    inline val skillManager get() = pl.skills!!
}
