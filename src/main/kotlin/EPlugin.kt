@file:Suppress("UNUSED")

package top.e404.eplugin

import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level
import java.util.logging.Logger

abstract class EPlugin : JavaPlugin() {
    companion object {
        private const val noperm = "&c无权限"
        private const val notPlayer = "&c仅玩家可用"
        private const val unknown = "&c未知指令"
        private const val invalidArgs = "&c无效参数"

        fun String.color() = replace("&", "§")

        // placeholder
        /**
         * 字符串批量替换占位符
         *
         * @param placeholder 占位符 格式为 <"world", world> 将会替换字符串中的 {world} 为 world
         * @return 经过替换的字符串
         */
        fun String.placeholder(placeholder: Map<String, Any>): String {
            var s = this
            for ((k, v) in placeholder.entries) s = s.replace("{$k}", v.toString())
            return s.color()
        }

        /**
         * 字符串批量替换占位符
         *
         * @param placeholder 占位符 格式为 <"world", world> 将会替换字符串中的 {world} 为 world
         * @return 经过替换的字符串
         */
        fun String.placeholder(vararg placeholder: Pair<String, Any>): String {
            var s = this
            for ((k, v) in placeholder) s = s.replace("{$k}", v.toString())
            return s.color()
        }

        private val regex = Regex("[.\\s\\-_]+")
        fun String.formatAsConst() = replace(regex, "_").uppercase()

        private val scheduler by lazy { Bukkit.getScheduler() }
    }

    abstract fun enableDebug(): Boolean
    abstract val prefix: String
    abstract val debugPrefix: String

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
    private val debugLogger by lazy { Logger.getLogger(debugPrefix) }

    /**
     * 接受debug信息的玩家
     */
    val debugUsers = mutableListOf<Player>()

    fun debug(msg: String) = "&b$msg".color().let { str ->
        debugUsers.forEach { sendMsgWithPrefix(it, str) }
        if (enableDebug()) debugLogger.info(str)
    }

    fun info(msg: String) =
        logger.info(msg.color())

    fun warn(msg: String, throwable: Throwable? = null) =
        if (throwable == null) logger.warning(msg.color())
        else logger.log(Level.WARNING, msg.color(), throwable)

    fun sendAndWarn(sender: CommandSender?, msg: String, t: Throwable? = null) {
        if (sender is Player) sendMsgWithPrefix(sender, msg)
        warn(msg, t)
    }

    fun withPrefix(s: String) = "$prefix $s".color()

    fun sendMsgWithPrefix(sender: CommandSender, s: String) =
        sender.sendMessage(withPrefix(s))

    /**
     * 发送无权限的消息
     */
    fun sendNoperm(sender: CommandSender) =
        sendMsgWithPrefix(sender, noperm)

    /**
     * 发送仅玩家可用的消息
     */
    fun sendNotPlayer(sender: CommandSender) =
        sendMsgWithPrefix(sender, notPlayer)

    /**
     * 发送未知指令的消息
     */
    fun sendUnknown(sender: CommandSender) =
        sendMsgWithPrefix(sender, unknown)

    /**
     * 发送无效参数的消息
     */
    fun sendInvalidArgs(sender: CommandSender) =
        sendMsgWithPrefix(sender, invalidArgs)

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
        if (notice) sendNoperm(sender)
        return false
    }

    // task
    fun runTask(task: () -> Unit) = scheduler.runTask(this, task)
    fun runTaskLater(delay: Long, task: () -> Unit) = scheduler.runTaskLater(this, task, delay)
    fun runTaskTimer(delay: Long, period: Long, task: () -> Unit) = scheduler.runTaskTimer(this, task, delay, period)

    // async task
    fun runTaskAsync(task: () -> Unit) = scheduler.runTaskAsynchronously(this, task)
    fun runTaskLaterAsync(delay: Long, task: () -> Unit) = scheduler.runTaskLaterAsynchronously(this, task, delay)
    fun runTaskTimerAsync(delay: Long, period: Long, task: () -> Unit) =
        scheduler.runTaskTimerAsynchronously(this, task, delay, period)
}