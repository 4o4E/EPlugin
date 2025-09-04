@file:Suppress("UNUSED")

package top.e404.eplugin.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.EPlugin.Companion.color

/**
 * 代表一个抽象的指令管理器
 *
 * @property plugin 插件实例
 * @property commands 指令列表
 * @since 1.0.0
 */
abstract class ECommandManager(
    val plugin: EPlugin,
    val name: String,
    val commands: MutableSet<ECommand>,
) : TabExecutor {
    constructor(
        plugin: EPlugin,
        name: String,
        vararg commands: ECommand
    ) : this(
        plugin,
        name,
        commands.toMutableSet()
    )

    fun register() = plugin.getCommand(name)?.also {
        it.setExecutor(this)
        it.tabCompleter = this
        plugin.debug { "成功注册指令${name}" }
    } ?: plugin.warn("跳过未注册的指令${name}")

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): Boolean {
        if (args.isEmpty()) {
            sender.sendHelp()
            return true
        }
        val head = args[0].lowercase()
        if (head.equals("help", true)) {
            sender.sendHelp()
            return true
        }
        val c = commands.firstOrNull { it.matchHead(head) }
        if (c == null) {
            plugin.sendUnknown(sender)
            return true
        }
        // 无权限
        if (!c.hasPerm(sender)) {
            plugin.sendNoperm(sender)
            plugin.info(
                """玩家${sender.name}尝试使用指令${command.name} ${args.joinToString(" ")}
                    由于缺少权限${c.permission.joinToString(", ")}而被阻止
                """.trimIndent()
            )
            return true
        }
        // 此指令只能由玩家执行 && 执行者不是玩家
        if (c.mustByPlayer && !plugin.isPlayer(sender)) return true
        try {
            c.onCommand(sender, args)
        } catch (e: FailException) {
            plugin.sendMsgWithPrefix(sender, e.message)
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>,
    ): MutableList<String> {
        val list = ArrayList<String>()
        val size = args.size // 最小只会为1
        val canUse = commands.filter { it.hasPerm(sender) }
        // 接管长度为1的指令的tab补全
        if (size == 1) return canUse.map { it.name }.toMutableList()
        // 其他长度
        val head = args[0]
        // 匹配指令头
        val c = canUse.firstOrNull { it.matchHead(head) }
        if (c == null || c.mustByPlayer && sender !is Player) return list
        // 传递给指令
        c.onTabComplete(sender, args, list)
        if (list.isEmpty()) return list
        val last = args.last()
        return list.filter { it.contains(last, true) }.toMutableList()
    }

    protected open fun CommandSender.sendHelp() {
        val help = commands
            .filter { it.hasPerm(this) && (!it.mustByPlayer || this is Player) }
            .joinToString("\n") { it.usage }
        plugin.run {
            description.run {
                val author =
                    if (authors.size == 1) authors.first()
                    else authors.toString()
                sendMessage("&7-=[ &6$name V$version&b by $author &7]=-\n${help}".color)
            }
        }
    }
}
