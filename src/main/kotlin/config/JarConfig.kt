package top.e404.eplugin.config

import org.bukkit.plugin.java.JavaPlugin

class JarConfig(val plugin: JavaPlugin, val path: String) : ConfigDefault {
    override val string by lazy { String(plugin.getResource(path)!!.readBytes()) }
}