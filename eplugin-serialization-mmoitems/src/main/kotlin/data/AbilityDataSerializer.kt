package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.Indyuce.mmoitems.stat.data.AbilityData
import top.e404.eplugin.config.serialization.primitive
import top.e404.eplugin.util.asJson
import top.e404.eplugin.util.gson

object AbilityDataSerializer : KSerializer<AbilityData> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = AbilityData(decoder.decodeString().asJson().asJsonObject)
    override fun serialize(encoder: Encoder, value: AbilityData) = encoder.encodeString(gson.toJson(value.toJson()))
}
