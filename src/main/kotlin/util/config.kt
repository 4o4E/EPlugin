@file:Suppress("UNUSED")

package top.e404.eplugin.util

import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin.Companion.formatAsConst

/**
 * 从配置文件中获得一个
 *
 * @param path 路径
 */
fun ConfigurationSection.getSoundConfig(path: String) =
    getConfigurationSection(path)?.let { cfg ->
        val t = cfg.getString("type") ?: return@let null
        val sound = Sound.valueOf(t.formatAsConst())
        val volume = cfg.getDouble("volume", 1.0).toFloat()
        val pitch = cfg.getDouble("pitch", 1.0).toFloat()
        SoundConfig(sound, volume, pitch)
    }

data class SoundConfig(
    var sound: Sound,
    var volume: Float,
    var pitch: Float
) {
    fun playAt(location: Location) = location.world?.playSound(location, sound, volume, pitch)

    fun playTo(p: Player) = p.playSound(p.location, sound, volume, pitch)
}

fun ConfigurationSection.getBooleanOrNull(path: String) = get(path)?.let { it as? Boolean }
fun ConfigurationSection.getIntOrNull(path: String) = get(path)?.let { it as? Int }
fun ConfigurationSection.getDoubleOrNull(path: String) = get(path)?.let { it as? Double }