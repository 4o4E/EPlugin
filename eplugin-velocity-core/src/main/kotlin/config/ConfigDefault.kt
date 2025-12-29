@file:Suppress("UNUSED")

package top.e404.eplugin.config

import top.e404.eplugin.EPluginVelocity

interface ConfigDefault {
    val string: String
}

class JarConfigDefault(val plugin: EPluginVelocity, val path: String) : ConfigDefault {
    override val string by lazy { plugin.getResourceAsText(path) }
}

class TextConfigDefault(override val string: String) : ConfigDefault
object EmptyConfigDefault : ConfigDefault {
    override val string = ""
}
