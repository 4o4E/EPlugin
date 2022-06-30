@file:Suppress("UNUSED")

package top.e404.eplugin.config

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import org.bukkit.command.CommandSender
import org.bukkit.scheduler.BukkitTask
import top.e404.eplugin.EPlugin

/**
 * 代表一个解析成文本的配置文件
 *
 * @property plugin 属于的插件
 * @property path 文件在插件数据目录中的路径
 * @property default 默认配置文件
 * @since 1.0.0
 */
abstract class ESerializationConfig<T : Any>(
    open val plugin: EPlugin,
    open val path: String,
    open val default: ConfigDefault = EmptyConfig,
    open val serializer: KSerializer<in T>,
    open val format: StringFormat = Yaml.default
) {
    val file by lazy { plugin.dataFolder.resolve(path) }
    lateinit var config: T

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
    fun load(sender: CommandSender?) {
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
    fun save(sender: CommandSender?) {
        try {
            file.writeText(onSave())
        } catch (t: Throwable) {
            val s = "保存配置文件`${path}`时出现异常"
            plugin.sendOrElse(sender, s) { plugin.warn(s, t) }
        }
    }

    var saveTask: BukkitTask? = null
    open val saveDurationTick: Long = 10 * 60 * 20

    fun scheduleSave() {
        if (saveTask != null) return
        saveTask = plugin.runTaskLater(saveDurationTick) {
            save(null)
            saveTask = null
        }
    }
}