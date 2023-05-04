package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.Indyuce.mmoitems.stat.data.SoundData
import top.e404.eplugin.serialization.mmoitems.data.SoundDataModel.Companion.toDataModel

object SoundDataSerializer : KSerializer<SoundData> {
    private val serializer = SoundDataModel.serializer()
    override val descriptor = serializer.descriptor
    override fun deserialize(decoder: Decoder) = serializer.deserialize(decoder).mmoitemsDeserialize()
    override fun serialize(encoder: Encoder, value: SoundData) = serializer.serialize(encoder, value.toDataModel())
}
