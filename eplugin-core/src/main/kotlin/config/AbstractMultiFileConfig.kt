@file:Suppress("UNUSED")

package top.e404.eplugin.config

import org.bukkit.command.CommandSender
import top.e404.eplugin.EPlugin
import java.io.File

/**
 * 代表一个多文件的配置文件
 */
abstract class AbstractMultiFileConfig<T: Any> {
    /**
     * 数据
     */
    open val config = mutableListOf<Pair<File, T>>()

    /**
     * 属于的插件
     */
    abstract val plugin: EPlugin

    /**
     * 储存的文件位置
     */
    abstract val dirPath: String

    /**
     * 文件夹File对象
     */
    val dir by lazy { plugin.dataFolder.resolve(dirPath) }

    /**
     * 保存默认配置文件
     *
     * @param sender 通知者
     */
    open fun saveDefault(sender: CommandSender?) {}

    /**
     * 从单个文件中加载
     *
     * @param file 文件
     * @return 加载结果
     */
    abstract fun loadFromSingleFile(file: File): T

    /**
     * 保存到单个文件
     *
     * @param file 文件
     * @param data 内容
     */
    abstract fun saveToSingleFile(file: File, data: T)

    /**
     * 递归获取子文件
     *
     * @param fileMap 文件map<路径, File对象>
     */
    protected fun File.files(fileMap: MutableMap<String, File>) {
        if (isFile) {
            fileMap[path] = this
            return
        }
        listFiles()?.forEach { it.files(fileMap) }
    }

    /**
     * 从所有文件中加载数据
     */
    open fun load(sender: CommandSender?) {
        saveDefault(sender)
        val files = buildMap { dir.files(this) }
        val config = mutableMapOf<String, T>()
        files.forEach { (path, file) ->
            try {
                config[path] = loadFromSingleFile(file)
            } catch (e: Exception) {
                plugin.sendAndWarn(sender, "文件${path}内容无效, 请检查文件, 此文件内容将跳过不加载", e)
                return@forEach
            }
        }
    }

    /**
     * 保存所有数据到对应的文件中
     */
    open fun save(sender: CommandSender?) {
        config.forEach { (file, config) ->
            file.parentFile.mkdirs()
            try {
                saveToSingleFile(file, config)
            } catch (e: Exception) {
                plugin.sendAndWarn(sender, "保存数据到文件${file.absolutePath}时出现异常", e)
                return@forEach
            }
        }
    }
}
