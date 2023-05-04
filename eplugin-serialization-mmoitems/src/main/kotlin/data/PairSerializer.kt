package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.PairSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.Indyuce.mmoitems.util.Pair

class PairSerializer<K : Any, V : Any>(kSerializer: KSerializer<K>, vSerializer: KSerializer<V>) : KSerializer<Pair<K, V>> {
    private val serializer = PairSerializer(kSerializer, vSerializer)
    override val descriptor = serializer.descriptor
    override fun deserialize(decoder: Decoder) = serializer.deserialize(decoder).let { (k, v) -> Pair.of(k, v) }
    override fun serialize(encoder: Encoder, value: Pair<K, V>) = serializer.serialize(encoder, value.key to value.value)
}
