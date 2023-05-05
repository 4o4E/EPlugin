@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import top.e404.eplugin.EPlugin.Companion.formatAsConst

object PotionEffectTypeSerializer : KSerializer<PotionEffectType> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = decoder.decodeString().formatAsConst().deserializeToPotionEffectType()
    override fun serialize(encoder: Encoder, value: PotionEffectType) = encoder.encodeString(value.name)
}

fun String.deserializeToPotionEffectType() = PotionEffectType.getByName(formatAsConst()) ?: throw Exception("cannot find effect type with name of $this")

object PotionEffectSerializer : KSerializer<PotionEffect> {
    private const val typeIndex = 0
    private const val durationIndex = 1
    private const val amplifierIndex = 2
    private const val ambientIndex = 3
    private const val particlesIndex = 4
    private const val colorIndex = 5

    override val descriptor = buildClassSerialDescriptor(this::class.java.name) {
        element<String>("type")
        element<Int>("duration")
        element<Int>("amplifier", isOptional = true)
        element<Boolean>("ambient", isOptional = true)
        element<Boolean>("particles", isOptional = true)
        element<Boolean>("icon", isOptional = true)
    }

    override fun deserialize(decoder: Decoder) = decoder.decodeStructure(descriptor) {
        var type = ""
        var duration = 0
        var amplifier = 0
        var ambient = true
        var particles = true
        var icon = true
        while (true) when (val index = decodeElementIndex(descriptor)) {
            typeIndex -> type = decoder.decodeString()
            durationIndex -> duration = decoder.decodeInt()
            amplifierIndex -> amplifier = decoder.decodeInt()
            ambientIndex -> ambient = decoder.decodeBoolean()
            particlesIndex -> particles = decoder.decodeBoolean()
            colorIndex -> icon = decoder.decodeBoolean()
            CompositeDecoder.DECODE_DONE -> break
            else -> error("Unexpected index: $index")
        }
        PotionEffect(type.deserializeToPotionEffectType(), duration, amplifier, ambient, particles, icon)
    }

    override fun serialize(encoder: Encoder, value: PotionEffect) = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, typeIndex, value.type.name)
        encodeIntElement(descriptor, durationIndex, value.duration)
        encodeIntElement(descriptor, amplifierIndex, value.amplifier)
        encodeBooleanElement(descriptor, ambientIndex, value.isAmbient)
        encodeBooleanElement(descriptor, particlesIndex, value.hasParticles())
        encodeBooleanElement(descriptor, colorIndex, value.hasIcon())
    }
}
