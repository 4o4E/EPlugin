@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin.Companion.formatAsConst
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

object SoundSerializer : KSerializer<Sound> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = Sound.valueOf(decoder.decodeString().formatAsConst())
    override fun serialize(encoder: Encoder, value: Sound) = encoder.encodeString(value.name)
}
