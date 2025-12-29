package top.e404.eplugin.config

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.MapSerializer
import top.e404.eplugin.EPluginVelocity

@Suppress("UNCHECKED_CAST")
open class KtxMultiFileMapConfig<K: Any, V: Any>(
    override val plugin: EPluginVelocity,
    override val dirPath: String,
    kSerializer: KSerializer<K>,
    vSerializer: KSerializer<V>,
    format: StringFormat = KtxConfig.defaultYaml
) : KtxMultiFileConfig<MutableMap<K, V>>(
    plugin = plugin,
    dirPath = dirPath,
    serializer = MapSerializer(kSerializer, vSerializer) as KSerializer<MutableMap<K, V>>,
    format = format
)
