package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.Indyuce.mmoitems.stat.data.random.RandomPotionEffectData
import top.e404.eplugin.serialization.mmoitems.data.RandomPotionEffectDataModel.Companion.toDataModel

object RandomPotionEffectDataSerializer : KSerializer<RandomPotionEffectData> {
    private val serializer = RandomPotionEffectDataModel.serializer()
    override val descriptor = serializer.descriptor
    override fun deserialize(decoder: Decoder) = serializer.deserialize(decoder).toItemData()
    override fun serialize(encoder: Encoder, value: RandomPotionEffectData) = serializer.serialize(encoder, value.toDataModel())
}
