package top.e404.eplugin.hook.psr

import me.rothes.protocolstringreplacer.ProtocolStringReplacer
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

@Suppress("UNUSED")
open class ProtocolStringReplacerHook(
    override val plugin: EPlugin,
) : EHook(plugin, "ProtocolStringReplacer") {
    val psr inline get() = ProtocolStringReplacer.getInstance()!!
}
