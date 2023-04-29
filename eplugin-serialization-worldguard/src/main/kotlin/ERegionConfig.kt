@file:Suppress("UNUSED")

package top.e404.eplugin.config

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import org.bukkit.Location
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.worldguard.WorldGuardHook

/**
 * 代表一个解析成文本的配置文件
 *
 * @property plugin 属于的插件
 * @property path 文件在插件数据目录中的路径
 * @property default 默认配置文件
 * @since 1.0.0
 */
abstract class ERegionConfig<F, T : RegionConfig<F>>(
    override val plugin: EPlugin,
    override val path: String,
    override val default: ConfigDefault = EmptyConfig,
    override val serializer: KSerializer<in T>,
    override val format: StringFormat = Yaml.default,
    val rgHook: WorldGuardHook
) : KtxConfig<T>(plugin, path, default, serializer, format) {
    fun <R> getConfig(location: Location, block: F.() -> R?): R? {
        if (rgHook.enable) rgHook.getRegion(location)?.let { region ->
            config.region[region.id]?.block()?.let { return it }
        }
        location.world?.name?.let { name ->
            config.each[name]?.block()?.let { return it }
        }
        return config.global.block()
    }
}
