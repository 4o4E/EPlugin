@file:Suppress("UNUSED")

package top.e404.eplugin.config

import org.bukkit.command.CommandSender
import org.bukkit.scheduler.BukkitTask
import top.e404.eplugin.EPlugin

/**
 * 代表一个配置文件
 *
 * @property plugin 属于的插件
 * @property path 文件在插件数据目录中的路径
 * @property default 默认配置文件
 * @since 1.0.2
 */
abstract class AbstractEConfig(
    override val plugin: EPlugin,
    open val path: String,
    open val default: ConfigDefault = EmptyConfigDefault,
): Savable {
    val file by lazy { plugin.dataFolder.resolve(path) }

    /**
     * 保存默认的配置文件
     *
     * @param sender 出现异常时的接收者
     * @since 1.0.2
     */
    abstract fun saveDefault(sender: CommandSender?)

    /**
     * 从文件加载配置文件
     *
     * @param sender 出现异常时的通知接收者
     * @since 1.0.2
     */
    abstract fun load(sender: CommandSender?)

    /**
     * 保存配置到文件
     *
     * @param sender 出现异常时的通知接收者
     * @since 1.0.0
     */
    abstract override fun save(sender: CommandSender?)

    override var saveTask: BukkitTask? = null
    override val saveDurationTick = 60 * 20L
}
