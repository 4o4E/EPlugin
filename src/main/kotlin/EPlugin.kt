@file:Suppress("UNUSED")

package top.e404.eplugin

import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import top.e404.eplugin.config.ELangManager

abstract class EPlugin : JavaPlugin() {
    companion object {
        fun String.color() = replace("&", "§")

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
            return s.color()
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
            return s.color()
        }

        private val regex = Regex("[.\\s\\-_]+")
        fun String.formatAsConst() = replace(regex, "_").uppercase()

        private val scheduler by lazy { Bukkit.getScheduler() }
    }

    private fun noperm() = langManager.getOrSelf("message.noperm")
    private fun notPlayer() = langManager.getOrSelf("message.non_player")
    private fun unknown() = langManager.getOrSelf("message.unknown_command")
    private fun invalidArgs() = langManager.getOrSelf("message.invalid_args")

    abstract fun enableDebug(): Boolean
    abstract val prefix: String
    abstract val debugPrefix: String
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

    private fun sendDebugMessage(str: String) {
        val msg = "$debugPrefix &b${str}".color()
        if (enableDebug()) console.sendMessage(msg)
        debuggers.forEach { Bukkit.getPlayer(it)?.sendMessage(msg) }
    }

    fun debug(msg: () -> String) {
        if (!enableDebug() && debuggers.isEmpty()) return
        sendDebugMessage(msg())
    }

    fun debug(path: String, vararg placeholder: Pair<String, Any>) {
        langManager.config.getString(path)?.let {
            sendDebugMessage(it.placeholder(*placeholder))
        } ?: sendDebugMessage(path)
    }

    fun info(msg: String) =
        sendMsgWithPrefix(console, "&f$msg".color())

    fun warn(msg: String, throwable: Throwable? = null) =
        if (throwable == null) sendMsgWithPrefix(console, "&e$msg".color())
        else sendMsgWithPrefix(console, "&e$msg\n&e${throwable.stackTraceToString()}".color())

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
    fun sendNoperm(sender: CommandSender) {
        sendMsgWithPrefix(sender, noperm())
    }

    /**
     * 发送仅玩家可用的消息
     */
    fun sendNotPlayer(sender: CommandSender) =
        sendMsgWithPrefix(sender, notPlayer())

    /**
     * 发送未知指令的消息
     */
    fun sendUnknown(sender: CommandSender) =
        sendMsgWithPrefix(sender, unknown())

    /**
     * 发送无效参数的消息
     */
    fun sendInvalidArgs(sender: CommandSender) =
        sendMsgWithPrefix(sender, invalidArgs())

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
    fun runTask(task: () -> Unit) = scheduler.runTask(this, task)
    fun runTaskLater(delay: Long, task: () -> Unit) = scheduler.runTaskLater(this, task, delay)
    fun runTaskTimer(delay: Long, period: Long, task: () -> Unit) = scheduler.runTaskTimer(this, task, delay, period)

    // async task
    fun runTaskAsync(task: () -> Unit) = scheduler.runTaskAsynchronously(this, task)
    fun runTaskLaterAsync(delay: Long, task: () -> Unit) = scheduler.runTaskLaterAsynchronously(this, task, delay)
    fun runTaskTimerAsync(delay: Long, period: Long, task: () -> Unit) =
        scheduler.runTaskTimerAsynchronously(this, task, delay, period)

    // cancel task
    fun cancelAllTask() = scheduler.cancelTasks(this)
    fun cancelTask(id: Int) = scheduler.cancelTask(id)
}