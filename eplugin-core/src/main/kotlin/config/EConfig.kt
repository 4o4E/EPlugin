@file:Suppress("UNUSED")

package top.e404.eplugin.config

import org.bukkit.command.CommandSender
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
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
abstract class EConfig(
    override val plugin: EPlugin,
    override val path: String,
    override val default: ConfigDefault = EmptyConfig,
    open val clearBeforeSave: Boolean = false,
) : AbstractEConfig(plugin, path, default) {
    var config: YamlConfiguration = YamlConfiguration()

    open operator fun get(path: String) = config.getString(path)
    operator fun set(path: String, value: Any?) = config.set(path, value)

    fun getOrDefault(path: String, default: String) = config.getString(path) ?: default
    fun getOrSelf(path: String) = getOrDefault(path, path)
    fun getOrElse(path: String, block: () -> String) = config.getString(path) ?: block()

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
            if (default is EmptyConfig) createNewFile()
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
    override fun save(sender: CommandSender?) {
        if (clearBeforeSave) config = YamlConfiguration()
        try {
            config.apply {
                onSave()
                save(file)
            }
            plugin.info("保存配置文件`${path}`完成")
        } catch (t: Throwable) {
            val s = "保存配置文件`${path}`时出现异常"
            plugin.sendOrElse(sender, s) { plugin.warn(s, t) }
        }
    }
}
