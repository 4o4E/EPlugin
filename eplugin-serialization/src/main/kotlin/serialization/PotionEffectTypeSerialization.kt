@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.potion.PotionEffectType

object PotionEffectTypeSerialization : KSerializer<PotionEffectType> {
    override val descriptor = PrimitiveSerialDescriptor(this::class.java.name, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): PotionEffectType = PotionEffectType.getByName(decoder.decodeString())!!
    override fun serialize(encoder: Encoder, value: PotionEffectType) = encoder.encodeString(value.name)
}