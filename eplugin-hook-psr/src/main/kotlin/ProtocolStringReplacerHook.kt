package top.e404.eplugin.hook.psr

import me.rothes.protocolstringreplacer.ProtocolStringReplacer
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

open class ProtocolStringReplacerHook(
    override val plugin: EPlugin,
) : EHook<ProtocolStringReplacer>(plugin, "ProtocolStringReplacer") {
    val psr: ProtocolStringReplacer
        get() = ProtocolStringReplacer.getInstance()!!
}