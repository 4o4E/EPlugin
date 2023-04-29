@file:Suppress("UNUSED")

package top.e404.eplugin.bungeecord

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Plugin
import org.bstats.bungeecord.Metrics
import top.e404.eplugin.bungeecord.config.ELangManager
import java.util.concurrent.TimeUnit
import java.util.logging.Level

abstract class EPlugin : Plugin() {
    companion object {
        fun String.color() = replace("&", "§")
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

        private val scheduler by lazy { ProxyServer.getInstance().scheduler }
    }

    private fun noperm() = langManager.getOrSelf("message.noperm")
    private fun notPlayer() = langManager.getOrSelf("message.non_player")
    private fun unknown() = langManager.getOrSelf("message.unknown_command")
    private fun invalidArgs() = langManager.getOrSelf("message.invalid_args")

    abstract var debug: Boolean
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
    private val console by lazy { ProxyServer.getInstance().console }

    /**
     * 接受debug信息的玩家
     */
    val debuggers = mutableSetOf<String>()

    private fun sendDebugMessage(str: String) {
        val msg = "$debugPrefix &b${str}".color()
        if (debug) console.sendMessage(TextComponent(msg))
        debuggers.forEach { ProxyServer.getInstance().getPlayer(it)?.sendMessage(TextComponent(msg)) }
    }

    fun debug(msg: () -> String) {
        if (!debug && debuggers.isEmpty()) return
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
        if (throwable == null) logger.log(Level.WARNING, msg)
        else logger.log(Level.WARNING, msg, throwable)

    fun sendAndWarn(sender: CommandSender?, msg: String, t: Throwable? = null) {
        if (sender is ProxiedPlayer) sendMsgWithPrefix(sender, msg)
        warn(msg, t)
    }

    fun withPrefix(s: String) = "$prefix $s".color()

    fun sendMsgWithPrefix(sender: CommandSender, s: String) =
        sender.sendMessage(TextComponent(withPrefix(s)))

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
        if (sender is ProxiedPlayer) sendMsgWithPrefix(sender, msg)
        else onElse()
    }

    fun isPlayer(sender: CommandSender, sendNotice: Boolean = true): Boolean {
        if (sender is ProxiedPlayer) return true
        if (sendNotice) sendNotPlayer(sender)
        return false
    }

    fun asPlayer(sender: CommandSender, notice: Boolean = true): ProxiedPlayer? {
        if (sender is ProxiedPlayer) return sender
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
    fun runTask(task: () -> Unit) = scheduler.schedule(this, task, 0L, TimeUnit.SECONDS)!!
    fun runTaskLater(delay: Long, unit: TimeUnit, task: () -> Unit) = scheduler.schedule(this, task, delay, unit)!!
    fun runTaskTimer(delay: Long, unit: TimeUnit, period: Long, task: () -> Unit) =
        scheduler.schedule(this, task, delay, period, unit)!!

    // async task
    fun runTaskAsync(task: () -> Unit) = scheduler.runAsync(this, task)

    // cancel task
    fun cancelAllTask() = scheduler.cancel(this)
    fun cancelTask(id: Int) = scheduler.cancel(id)
}
