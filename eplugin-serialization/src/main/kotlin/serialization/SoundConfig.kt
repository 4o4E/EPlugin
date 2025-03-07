@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import top.e404.eplugin.config.BkSerializable
import top.e404.eplugin.config.buildYamlConfiguration
import top.e404.eplugin.config.getFloat

@Serializable
data class SoundConfig(
    var type: String,
    var volume: Float,
    var pitch: Float,
) : BkSerializable {
    private inline val format get() = if (':' in type) type else "minecraft:$type"

    fun playAt(location: Location) = location.world?.playSound(location, format, volume, pitch)

    fun playTo(p: Player) = p.playSound(p.location, format, volume, pitch)

    fun playTo(players: Collection<Player>) = players.forEach(::playTo)

    companion object {
        init {
            BkSerializable.regBkSerializable<SoundConfig>()
        }

        @JvmStatic
        fun deserialize(data: ConfigurationSection) = SoundConfig(
            data.getString("type")!!,
            data.getFloat("volume"),
            data.getFloat("pitch"),
        )
    }

    override fun serialize() = buildYamlConfiguration {
        set("type", type)
        set("volume", volume)
        set("pitch", pitch)
    }
}