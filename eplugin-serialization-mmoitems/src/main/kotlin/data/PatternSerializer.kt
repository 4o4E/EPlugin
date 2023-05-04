package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.block.banner.Pattern
import top.e404.eplugin.serialization.mmoitems.data.PatternModel.Companion.toDataModel

object PatternSerializer : KSerializer<Pattern> {
    private val serializer = PatternModel.serializer()
    override val descriptor = serializer.descriptor
    override fun deserialize(decoder: Decoder) = serializer.deserialize(decoder).mmoitemsDeserialize()
    override fun serialize(encoder: Encoder, value: Pattern) = serializer.serialize(encoder, value.toDataModel())
}
