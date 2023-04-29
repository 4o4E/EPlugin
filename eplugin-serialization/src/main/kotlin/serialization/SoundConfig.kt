@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin.Companion.formatAsConst

@Serializable
data class SoundConfig(
    var type: String,
    var volume: Float,
    var pitch: Float,
) {
    private inline val format get() = if (':' in type) type else "minecraft:$type"

    fun playAt(location: Location) = location.world?.playSound(location, format, volume, pitch)

    fun playTo(p: Player) = p.playSound(p.location, format, volume, pitch)

    fun playTo(players: Collection<Player>) = players.forEach(::playTo)
}

object SoundSerializer : KSerializer<Sound> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = Sound.valueOf(decoder.decodeString().formatAsConst())
    override fun serialize(encoder: Encoder, value: Sound) = encoder.encodeString(value.name)
}
