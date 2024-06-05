@file:Suppress("UNUSED")

package top.e404.eplugin

import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import org.bukkit.scheduler.BukkitRunnable
import top.e404.eplugin.config.ELangManager
import top.e404.eplugin.util.forEachOnline
import top.e404.eplugin.util.forEachOp
import java.io.File
import java.util.logging.Level

abstract class EPlugin : JavaPlugin {
    constructor() : super()
    constructor(
        loader: JavaPluginLoader,
        description: PluginDescriptionFile,
        dataFolder: File,
        file: File
    ) : super(loader, description, dataFolder, file)

    companion object {
        val String.color get() = replace("&", "§")
        private val colorRegex = Regex("(?i)[§&][\\da-fk-orx]")
        fun String.removeColor() = replace(colorRegex, "")

        // placeholder
        /**
         * 字符串批量替换占位符
         *
         * @param placeholder 占位符 格式为 <"world", world> 将会替换字符串中的 {world} 为 world
         * @return 经过替换的字符串
         */
        fun String.placeholder(placeholder: Map<String, Any?>): String {
            var s = this
            for ((k, v) in placeholder.entries) s = s.replace("{$k}", v.toString())
            return s.color
        }

        /**
         * 字符串批量替换占位符
         *
         * @param placeholder 占位符 格式为 <"world", world> 将会替换字符串中的 {world} 为 world
         * @return 经过替换的字符串
         */
        fun String.placeholder(vararg placeholder: Pair<String, Any?>): String {
            var s = this
            for ((k, v) in placeholder) s = s.replace("{$k}", v.toString())
            return s.color
        }

        private val regex = Regex("[.\\s\\-_]+")
        fun String.formatAsConst() = replace(regex, "_").uppercase()

        private val scheduler inline get() = Bukkit.getScheduler()
    }

    private val noperm get() = langManager.getOrSelf("message.noperm")
    private val notPlayer get() = langManager.getOrSelf("message.non_player")
    private val unknown get() = langManager.getOrSelf("message.unknown_command")
    private val invalidArgs get() = langManager.getOrSelf("message.invalid_args")

    open val prefix: String get() = langManager.getOrElse("prefix") { "" }
    open val debugPrefix: String get() = langManager.getOrElse("debug_prefix") { "" }
    abstract var debug: Boolean
    abstract val langManager: ELangManager

    // bstats

    /**
     * bstats 插件id
     */
    open val bstatsId: Int? = null

    /**
     * Metrics实例
     */
    var metrics: Metrics? = null

    /**
     * 创建Metrics实例
     */
    fun bstats() {
        bstatsId?.let { metrics = Metrics(this, it) }
    }

    // logger & message

    /**
     * 输出debug信息的logger
     */
    private val console by lazy { Bukkit.getConsoleSender() }

    /**
     * 接受debug信息的玩家
     */
    val debuggers = mutableSetOf<String>()

    /**
     * 广播消息(包括控制台和玩家)
     *
     * @param message 消息
     */
    fun broadcastMsg(message: String) {
        info(message)
        forEachOnline { sendMsgWithPrefix(it, message) }
    }

    /**
     * 向在线的op发送消息
     *
     * @param message 消息
     */
    fun sendOpMsg(message: String) = forEachOp { sendMsgWithPrefix(it, message) }

    fun sendDebugMessage(str: String) {
        val msg = "$debugPrefix &b${str}".color
        if (debug) console.sendMessage(msg)
        debuggers.forEach { Bukkit.getPlayer(it)?.sendMessage(msg) }
    }

    inline fun debug(msg: () -> String) {
        if (!debug && debuggers.isEmpty()) return
        sendDebugMessage(msg())
    }

    fun debug(path: String, vararg placeholder: Pair<String, Any>) {
        langManager.config.getString(path)?.let {
            sendDebugMessage(it.placeholder(*placeholder))
        } ?: sendDebugMessage(path)
    }

    inline fun buildDebug(block: StringBuilder.() -> Unit) {
        if (!debug && debuggers.isEmpty()) return
        sendDebugMessage(buildString(block))
    }

    fun info(msg: String) = sendMsgWithPrefix(console, "&f$msg".color)

    fun warn(msg: String, throwable: Throwable? = null) =
        if (throwable == null) logger.log(Level.WARNING, msg)
        else logger.log(Level.WARNING, msg, throwable)

    fun sendAndWarn(sender: CommandSender?, msg: String, t: Throwable? = null) {
        if (sender is Player) sendMsgWithPrefix(sender, msg)
        warn(msg, t)
    }

    fun withPrefix(s: String) = "$prefix $s".color

    fun sendMsgWithPrefix(sender: CommandSender, s: String) = sender.sendMessage(withPrefix(s))

    /**
     * 发送无权限的消息
     */
    fun sendNoperm(sender: CommandSender) = sendMsgWithPrefix(sender, noperm)

    /**
     * 发送仅玩家可用的消息
     */
    fun sendNotPlayer(sender: CommandSender) = sendMsgWithPrefix(sender, notPlayer)

    /**
     * 发送未知指令的消息
     */
    fun sendUnknown(sender: CommandSender) = sendMsgWithPrefix(sender, unknown)

    /**
     * 发送无效参数的消息
     */
    fun sendInvalidArgs(sender: CommandSender) = sendMsgWithPrefix(sender, invalidArgs)

    fun sendOrElse(sender: CommandSender?, msg: String, onElse: () -> Unit) {
        if (sender is Player) sendMsgWithPrefix(sender, msg)
        else onElse()
    }

    fun isPlayer(sender: CommandSender, sendNotice: Boolean = true): Boolean {
        if (sender is Player) return true
        if (sendNotice) sendNotPlayer(sender)
        return false
    }

    fun asPlayer(sender: CommandSender, notice: Boolean = true): Player? {
        if (sender is Player) return sender
        if (notice) sendNotPlayer(sender)
        return null
    }

    fun hasPerm(sender: CommandSender, perm: String, notice: Boolean = true): Boolean {
        if (sender.hasPermission(perm)) return true
        if (notice) {
            sendNoperm(sender)
            info("${sender.name}缺少权限${perm}")
        }
        return false
    }

    // task
    fun runTask(task: (BukkitRunnable) -> Unit) = converse(task).runTask(this)
    fun runTaskLater(delay: Long, task: (BukkitRunnable) -> Unit) = converse(task).runTaskLater(this, delay)

    fun runTaskTimer(delay: Long, period: Long, task: (BukkitRunnable) -> Unit) =
        converse(task).runTaskTimer(this, delay, period)

    // async task
    fun runTaskAsync(task: (BukkitRunnable) -> Unit) = converse(task).runTaskAsynchronously(this)
    fun runTaskLaterAsync(delay: Long, task: (BukkitRunnable) -> Unit) =
        converse(task).runTaskLaterAsynchronously(this, delay)

    fun runTaskTimerAsync(delay: Long, period: Long, task: (BukkitRunnable) -> Unit) =
        converse(task).runTaskTimerAsynchronously(this, delay, period)

    private fun converse(task: ((BukkitRunnable) -> Unit)) = InternalRunnable(task) as BukkitRunnable

    internal class InternalRunnable(private val task: (BukkitRunnable) -> Unit) : BukkitRunnable() {
        override fun run() = task.invoke(this)
    }

    // cancel task
    fun cancelAllTask() = scheduler.cancelTasks(this)
    fun cancelTask(id: Int) = scheduler.cancelTask(id)

    // resource
    fun getResourceAsBytes(path: String) = classLoader.getResource(path)
        ?.readBytes()
        ?: error("can't find resource with path: $path")

    fun getResourceAsText(path: String) = classLoader.getResource(path)
        ?.readText()
        ?: error("can't find resource with path: $path")
}
