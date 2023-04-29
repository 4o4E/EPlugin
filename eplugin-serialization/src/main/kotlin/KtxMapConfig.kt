@file:Suppress("UNUSED")

package top.e404.eplugin.config

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.MapSerializer
import org.bukkit.command.CommandSender
import top.e404.eplugin.EPlugin
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * 代表一个解析成文本的配置文件
 *
 * @property plugin 属于的插件
 * @property path 文件在插件数据目录中的路径
 * @property default 默认配置文件
 * @since 1.0.0
 */
abstract class KtxMapConfig<K : Any, V : Any>(
    plugin: EPlugin,
    path: String,
    default: ConfigDefault = EmptyConfigDefault,
    kSerializer: KSerializer<K>,
    vSerializer: KSerializer<V>,
    format: StringFormat = defaultYaml,
    val synchronized: Boolean = false,
) : KtxConfig<MutableMap<K, V>>(
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

    override fun load(sender: CommandSender?) {
        saveDefault(sender)
        @Suppress("UNCHECKED_CAST")
        config = format.decodeFromString(serializer, file.readText()) as MutableMap<K, V>
        if (synchronized) config = ConcurrentHashMap(config)
        onLoad(config, sender)
    }

    fun getOrPut(key: K, defaultValue: () -> V) = config.getOrPut(key, defaultValue)
    fun getOrDefault(key: K, value: () -> V) = config.getOrDefault(key, value)
    fun getOrThrow(key: K) = config[key] ?: throw NoSuchElementException("cannot find element with key $key")
}
