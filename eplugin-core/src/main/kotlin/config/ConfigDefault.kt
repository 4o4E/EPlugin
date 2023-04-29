@file:Suppress("UNUSED")

package top.e404.eplugin.config

import org.bukkit.plugin.java.JavaPlugin

interface ConfigDefault {
    val string: String
}

class JarConfigDefault(val plugin: JavaPlugin, val path: String) : ConfigDefault {
    override val string by lazy { plugin.getResource(path)!!.use { it.readBytes().toString(Charsets.UTF_8) } }
}

class TextConfigDefault(override val string: String) : ConfigDefault
object EmptyConfigDefault : ConfigDefault {
    override val string = ""
}
