package top.e404.eplugin.util

import org.bukkit.configuration.serialization.ConfigurationSerializable

fun ConfigurationSerializable.serializeRecursion(): MutableMap<String, Any> {
    val map = this.serialize()
    return map.onEach { (k, v) ->
        if (v is ConfigurationSerializable) map[k] = v.serializeRecursion()
    }
}
