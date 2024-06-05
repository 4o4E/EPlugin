package top.e404.eplugin.hook.protocollib

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

@Suppress("UNUSED")
open class ProtocolLibHook(
    override val plugin: EPlugin,
) : EHook(plugin, "ProtocolLib") {
    val api: ProtocolManager = ProtocolLibrary.getProtocolManager()
}
