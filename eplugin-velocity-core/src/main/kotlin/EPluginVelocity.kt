@file:Suppress("UNUSED")

package top.e404.eplugin

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.plugin.PluginContainer
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.scheduler.ScheduledTask
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bstats.velocity.Metrics
import org.slf4j.Logger
import top.e404.eplugin.EPluginVelocity.Companion.placeholder
import top.e404.eplugin.config.ELangManager
import java.io.File
import java.nio.file.Path
import java.util.concurrent.TimeUnit
import kotlin.jvm.optionals.getOrNull

abstract class EPluginVelocity(
    open val server: ProxyServer,
    open val logger: Logger,
    open val container: PluginContainer,
    @param:DataDirectory open val dataDir: Path,
    open val metricsFactory: Metrics.Factory
) {
    val dataFolder: File = dataDir.toFile().also { it.mkdirs() }

    companion object {
        val String.color get() = replace("&", "§")
        val String.component get() = LegacyComponentSerializer.legacySection().deserialize(this)
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
        bstatsId?.let { metrics = metricsFactory.make(this, it) }
    }

    // logger & message

    /**
     * 输出debug信息的logger
     */
    private val console by lazy { server.consoleCommandSource }

    /**
     * 接受debug信息的玩家
     */
    val debuggers = mutableSetOf<String>()

    fun sendDebugMessage(str: String, highlight: Boolean = true) {
        val msg = if (highlight) "$debugPrefix &b${str}".color else "$debugPrefix &b".color + str
        if (debug) console.sendMessage(msg.component)
        debuggers.forEach { server.getPlayer(it).getOrNull()?.sendMessage(msg.component) }
    }

    inline fun debug(highlight: Boolean = true, msg: () -> String) {
        if (!debug && debuggers.isEmpty()) return
        sendDebugMessage(msg(), highlight)
    }

    fun debug(path: String, vararg placeholder: Pair<String, Any>) {
        langManager.getString(path)?.let {
            sendDebugMessage(it.placeholder(*placeholder))
        } ?: sendDebugMessage(path)
    }

    inline fun buildDebug(block: StringBuilder.() -> Unit) {
        if (!debug && debuggers.isEmpty()) return
        sendDebugMessage(buildString(block))
    }

    fun info(msg: String) = sendMsgWithPrefix(console, "&f$msg".color)

    fun warn(msg: String, throwable: Throwable? = null) =
        if (throwable == null) logger.warn(msg)
        else logger.warn(msg, throwable)

    fun sendAndWarn(sender: CommandSource?, msg: String, t: Throwable? = null) {
        if (sender is Player) sendMsgWithPrefix(sender, msg)
        warn(msg, t)
    }

    fun withPrefix(s: String) = "$prefix $s".color

    fun sendMsgWithPrefix(sender: CommandSource, s: String) = sender.sendMessage(withPrefix(s).component)

    /**
     * 发送无权限的消息
     */
    fun sendNoperm(sender: CommandSource) = sendMsgWithPrefix(sender, noperm)

    /**
     * 发送仅玩家可用的消息
     */
    fun sendNotPlayer(sender: CommandSource) = sendMsgWithPrefix(sender, notPlayer)

    /**
     * 发送未知指令的消息
     */
    fun sendUnknown(sender: CommandSource) = sendMsgWithPrefix(sender, unknown)

    /**
     * 发送无效参数的消息
     */
    fun sendInvalidArgs(sender: CommandSource) = sendMsgWithPrefix(sender, invalidArgs)

    fun sendOrElse(sender: CommandSource?, msg: String, onElse: () -> Unit) {
        if (sender is Player) sendMsgWithPrefix(sender, msg)
        else onElse()
    }

    fun isPlayer(sender: CommandSource, sendNotice: Boolean = true): Boolean {
        if (sender is Player) return true
        if (sendNotice) sendNotPlayer(sender)
        return false
    }

    fun asPlayer(sender: CommandSource, notice: Boolean = true): Player? {
        if (sender is Player) return sender
        if (notice) sendNotPlayer(sender)
        return null
    }

    fun hasPerm(sender: CommandSource, perm: String, notice: Boolean = true): Boolean {
        if (sender.hasPermission(perm)) return true
        if (notice) {
            sendNoperm(sender)
        }
        return false
    }

    // task
    private fun ((ScheduledTask) -> Unit).toTask() = server.scheduler.buildTask(this@EPluginVelocity, this)
    fun runTask(task: (ScheduledTask) -> Unit): ScheduledTask = task.toTask().schedule()
    fun runTaskLater(delay: Long, task: (ScheduledTask) -> Unit): ScheduledTask = task.toTask()
        .delay(delay, TimeUnit.MILLISECONDS)
        .schedule()

    fun runTaskTimer(delay: Long, period: Long, task: (ScheduledTask) -> Unit): ScheduledTask = task.toTask()
            .delay(delay, TimeUnit.MILLISECONDS)
            .repeat(period, TimeUnit.MILLISECONDS)
            .schedule()

    // async task
    val runTaskAsync = ::runTask
    val runTaskLaterAsync = ::runTaskLater
    val runTaskTimerAsync = ::runTaskTimer

    // cancel task
    fun cancelAllTask() = server.scheduler.tasksByPlugin(this).forEach { it.cancel() }

    // resource
    fun getResourceAsBytes(path: String) = this.javaClass.getResource("/$path")
        ?.readBytes()
        ?: error("can't find resource with path: $path")

    fun getResourceAsText(path: String) = this.javaClass.getResource("/$path")
        ?.readText()
        ?: error("can't find resource with path: /$path")
}
