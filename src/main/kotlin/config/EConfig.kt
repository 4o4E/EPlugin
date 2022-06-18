package top.e404.eplugin.config

import org.bukkit.command.CommandSender
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import top.e404.eplugin.EPlugin

/**
 * 代表一个配置文件
 *
 * @property plugin 属于的插件
 * @property path 文件在插件数据目录中的路径
 * @property default 默认配置文件
 * @property clearBeforeSave 在保存文件之前是否将[config]替换成新的[YamlConfiguration]
 * @since 1.0.0
 */
@Suppress("UNUSED")
abstract class EConfig(
    val plugin: EPlugin,
    val path: String,
    val default: ConfigDefault? = null,
    val clearBeforeSave: Boolean = false,
) {
    companion object {
        interface ConfigDefault {
            fun getString(): String
        }

        class JarConfig(val plugin: JavaPlugin, val path: String) : ConfigDefault {
            override fun getString() = String(plugin.getResource(path)!!.readBytes())
        }

        class TextConfig(val text: String) : ConfigDefault {
            override fun getString() = text
        }
    }

    val file by lazy { plugin.dataFolder.resolve(path) }
    lateinit var config: YamlConfiguration

    /**
     * 保存默认的配置文件
     *
     * @param sender 出现异常时的接收者
     * @since 1.0.0
     */
    fun saveDefault(sender: CommandSender?) {
        if (file.exists()) return
        file.runCatching {
            if (!parentFile.exists()) parentFile.mkdirs()
            if (isDirectory) {
                val s = "`${path}`是目录, 请手动删除或重命名"
                plugin.sendOrElse(sender, s) { plugin.warn(s) }
                return
            }
            if (default == null) createNewFile()
            else writeText(default.getString())
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
    fun load(sender: CommandSender?) {
        saveDefault(sender)
        val nc = YamlConfiguration()
        try {
            nc.load(file)
        } catch (e: InvalidConfigurationException) {
            val s = "配置文件`${path}`格式错误, 请检查配置文件, 此文件内容将不会重载"
            plugin.sendOrElse(sender, s) { plugin.warn(s, e) }
            plugin.sendAndWarn(sender, s, e)
            return
        } catch (t: Throwable) {
            val s = "加载配置文件`${path}`时出现异常, 此文件内容将不会重载"
            plugin.sendOrElse(sender, s) { plugin.warn(s, t) }
            return
        }
        nc.onLoad()
        config = nc
    }

    open fun YamlConfiguration.onLoad() {}
    open fun YamlConfiguration.onSave() {}

    /**
     * 保存配置到文件
     *
     * @param sender 出现异常时的通知接收者
     * @since 1.0.0
     */
    fun save(sender: CommandSender?) {
        if (clearBeforeSave) config = YamlConfiguration()
        try {
            config.apply {
                onSave()
                save(file)
            }
        } catch (t: Throwable) {
            val s = "保存配置文件`${path}`时出现异常"
            plugin.sendOrElse(sender, s) { plugin.warn(s, t) }
        }
    }
}