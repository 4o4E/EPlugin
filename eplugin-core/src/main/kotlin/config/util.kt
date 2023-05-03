package top.e404.eplugin.config

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration

fun List<Regex>.matches(string: String) = any { it.matches(string) }

inline fun buildYamlConfiguration(block: YamlConfiguration.() -> Unit) = YamlConfiguration().apply(block)

fun ConfigurationSection.getFloat(path: String) = when (val v = get(path)) {
    is Number -> v.toFloat()
    is String -> v.toFloat()
    else -> 0F
}
