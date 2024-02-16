@file:Suppress("UNUSED")

package top.e404.eplugin.config

import com.charleskorn.kaml.PolymorphismStyle
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import org.bukkit.command.CommandSender
import top.e404.eplugin.EPlugin

/**
 * 代表一个解析成文本的配置文件
 *
 * @property plugin 属于的插件
 * @property path 文件在插件数据目录中的路径
 * @property default 默认配置文件
 * @since 1.0.0
 */
abstract class KtxConfig<T : Any>(
    override val plugin: EPlugin,
    override val path: String,
    override val default: ConfigDefault = EmptyConfigDefault,
    open val serializer: KSerializer<in T>,
    open val format: StringFormat = defaultYaml,
) : AbstractEConfig(plugin, path, default) {
    companion object {
        val defaultYaml by lazy {
            Yaml(
                configuration = YamlConfiguration(
                    strictMode = false,
                    polymorphismStyle = PolymorphismStyle.Property
                )
            )
        }
    }

    lateinit var config: T
        internal set

    /**
     * 保存默认的配置文件
     *
     * @param sender 出现异常时的接收者
     * @since 1.0.0
     */
    override fun saveDefault(sender: CommandSender?) {
        if (file.exists()) return
        file.runCatching {
            if (!parentFile.exists()) parentFile.mkdirs()
            if (isDirectory) {
                val s = "`${path}`是目录, 请手动删除或重命名"
                plugin.sendOrElse(sender, s) { plugin.warn(s) }
                return
            }
            writeText(default.string)
        }.onFailure {
            val s = "保存默认配置文件`${path}`时出现异常"
            plugin.sendOrElse(sender, s) { plugin.warn(s, it) }
        }
    }

    /**
     * 从文件加载配置文件
     *
     * @param sender 出现异常时的通知接收者
     * @since 1.0.0
     */
    override fun load(sender: CommandSender?) {
        saveDefault(sender)
        @Suppress("UNCHECKED_CAST")
        config = format.decodeFromString(serializer, file.readText()) as T
        onLoad(config, sender)
    }

    open fun onLoad(config: T, sender: CommandSender? = null) {}
    open fun onSave() = format.encodeToString(serializer, config)

    /**
     * 保存配置到文件
     *
     * @param sender 出现异常时的通知接收者
     * @since 1.0.0
     */
    override fun save(sender: CommandSender?) {
        try {
            file.writeText(onSave())
            plugin.info("保存配置文件`${path}`完成")
        } catch (t: Throwable) {
            val s = "保存配置文件`${path}`时出现异常"
            plugin.sendOrElse(sender, s) { plugin.warn(s, t) }
        }
    }
}
