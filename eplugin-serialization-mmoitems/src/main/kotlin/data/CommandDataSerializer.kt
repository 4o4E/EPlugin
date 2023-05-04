package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.Indyuce.mmoitems.stat.data.CommandData
import top.e404.eplugin.serialization.mmoitems.data.CommandDataModel.Companion.toDataModel

object CommandDataSerializer : KSerializer<CommandData> {
    private val serializer = CommandDataModel.serializer()
    override val descriptor = serializer.descriptor
    override fun deserialize(decoder: Decoder) = serializer.deserialize(decoder).mmoitemsDeserialize()
    override fun serialize(encoder: Encoder, value: CommandData) = serializer.serialize(encoder, value.toDataModel())
}
