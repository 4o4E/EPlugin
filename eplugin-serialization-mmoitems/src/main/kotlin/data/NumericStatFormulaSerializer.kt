package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.Indyuce.mmoitems.api.util.NumericStatFormula
import top.e404.eplugin.serialization.mmoitems.data.NumericStatFormulaModel.Companion.toDataModel

object NumericStatFormulaSerializer : KSerializer<NumericStatFormula> {
    private val serializer = NumericStatFormulaModel.serializer()
    override val descriptor = serializer.descriptor
    override fun deserialize(decoder: Decoder) = serializer.deserialize(decoder).toRandomStatData()
    override fun serialize(encoder: Encoder, value: NumericStatFormula) = serializer.serialize(encoder, value.toDataModel())
}
