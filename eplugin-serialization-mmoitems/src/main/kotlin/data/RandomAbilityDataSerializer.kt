package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.Indyuce.mmoitems.stat.data.random.RandomAbilityData
import top.e404.eplugin.serialization.mmoitems.data.RandomAbilityDataModel.Companion.toDataModel

object RandomAbilityDataSerializer : KSerializer<RandomAbilityData> {
    private val serializer = RandomAbilityDataModel.serializer()
    override val descriptor = serializer.descriptor
    override fun deserialize(decoder: Decoder) = serializer.deserialize(decoder).toItemData()
    override fun serialize(encoder: Encoder, value: RandomAbilityData) = serializer.serialize(encoder, value.toDataModel())
}
