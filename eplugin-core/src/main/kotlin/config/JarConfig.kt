package top.e404.eplugin.config

import org.bukkit.plugin.java.JavaPlugin

class JarConfig(val plugin: JavaPlugin, val path: String) : ConfigDefault {
    override val string by lazy { plugin.getResource(path)!!.use { it.readBytes().toString(Charsets.UTF_8) } }
}
