@file:Suppress("UNUSED")

package top.e404.eplugin.config

import org.bukkit.command.CommandSender
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.file.YamlConstructor
import org.bukkit.configuration.file.YamlRepresenter
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import top.e404.eplugin.EPlugin

/**
 * 代表一个使用ConfigurationSerializable序列化和反序列化的配置文件
 *
 * @property plugin 属于的插件
 * @property path 文件在插件数据目录中的路径
 * @property default 默认配置文件
 * @since 1.0.0
 */
abstract class YamlSerializationConfig<T : BkSerializable>(
    override val plugin: EPlugin,
    override val path: String,
    override val default: ConfigDefault = EmptyConfigDefault,
    val cls: Class<T>
) : AbstractEConfig(plugin, path, default) {
    lateinit var config: T
    private val yaml = Yaml(YamlConstructor(), YamlRepresenter(), DumperOptions())

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
            if (default is EmptyConfigDefault) createNewFile()
            else writeText(default.string)
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
        val nc = YamlConfiguration()
        try {
            nc.load(file)
        } catch (e: InvalidConfigurationException) {
            plugin.sendAndWarn(sender, "配置文件`${path}`格式错误, 请检查配置文件, 此文件内容将不会重载", e)
            return
        } catch (t: Throwable) {
            plugin.sendAndWarn(sender, "加载配置文件`${path}`时出现异常, 此文件内容将不会重载", t)
            return
        }
        config = BkSerializable.deserialize(nc, cls)
    }

    /**
     * 保存配置到文件
     *
     * @param sender 出现异常时的通知接收者
     * @since 1.0.0
     */
    override fun save(sender: CommandSender?) {
        try {
            file.writeText(yaml.dump(config.serialize().getValues(false)))
            plugin.info("保存配置文件`${path}`完成")
        } catch (t: Throwable) {
            val s = "保存配置文件`${path}`时出现异常"
            plugin.sendOrElse(sender, s) { plugin.warn(s, t) }
        }
    }
}
