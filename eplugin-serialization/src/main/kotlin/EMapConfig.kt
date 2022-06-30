@file:Suppress("UNUSED")

package top.e404.eplugin.config

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.MapSerializer
import top.e404.eplugin.EPlugin

/**
 * 代表一个解析成文本的配置文件
 *
 * @property plugin 属于的插件
 * @property path 文件在插件数据目录中的路径
 * @property default 默认配置文件
 * @since 1.0.0
 */
abstract class EMapConfig<K : Any, V : Any>(
    plugin: EPlugin,
    path: String,
    default: ConfigDefault = EmptyConfig,
    kSerializer: KSerializer<K>,
    vSerializer: KSerializer<V>,
    format: StringFormat = Yaml.default
) : ESerializationConfig<MutableMap<K, V>>(
    plugin,
    path,
    default,
    MapSerializer(kSerializer, vSerializer),
    format
) {
    operator fun get(key: K) = config[key]
    operator fun set(key: K, value: V) {
        config[key] = value
    }

    fun getOrPut(key: K, defaultValue: () -> V) = config.getOrPut(key, defaultValue)
    fun getOrDefault(key: K, value: () -> V) = config.getOrDefault(key, value)
}