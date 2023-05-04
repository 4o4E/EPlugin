package top.e404.eplugin.serialization.mmoitems.data

import io.lumine.mythic.lib.skill.trigger.TriggerType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import top.e404.eplugin.config.serialization.primitive

object TriggerTypeSerializer : KSerializer<TriggerType> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = TriggerType.valueOf(decoder.decodeString())
    override fun serialize(encoder: Encoder, value: TriggerType) = encoder.encodeString(value.name())
}
