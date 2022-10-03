package top.e404.eplugin.bungeecord.config

import net.md_5.bungee.api.plugin.Plugin

class JarConfig(val plugin: Plugin, val path: String) : ConfigDefault {
    override val string by lazy { plugin.getResourceAsStream(path)!!.use { it.readBytes().toString(Charsets.UTF_8) } }
}