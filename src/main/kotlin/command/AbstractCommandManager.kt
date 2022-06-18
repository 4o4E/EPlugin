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
@Suppress("UNUSED")
abstract class AbstractCommandManager(
    val plugin: EPlugin,
    val name: String,
    val commands: MutableList<AbstractCommand>,
) : TabExecutor {
    constructor(
        plugin: EPlugin,
        name: String,
        vararg commands: AbstractCommand
    ) : this(
        plugin,
        name,
        commands.toMutableList()
    )

    fun register() = plugin.getCommand(name)?.also {
        it.setExecutor(this)
        it.tabCompleter = this
        plugin.debug("成功注册指令${name}")
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
        if (head == "help") {
            sender.sendHelp()
            return true
        }
        val c = commands.firstOrNull { it.name.equals(head, true) }
        if (c == null) {
            plugin.sendUnknown(sender)
            return true
        }
        // 无权限
        if (!c.hasPerm(sender)) {
            plugin.sendNoperm(sender)
            return true
        }
        // 此指令只能由玩家执行 && 执行者不是玩家
        if (c.mustByPlayer
            && !plugin.isPlayer(sender)
        ) return true
        c.onCommand(sender, args)
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
        if (
            c == null
            || c.mustByPlayer && sender !is Player
        ) return list
        // 传递给指令
        c.onTabComplete(sender, args, list)
        val last = args.last()
        return list.filter { it.contains(last, true) }.toMutableList()
    }

    private fun CommandSender.sendHelp() {
        val help = commands
            .filter { it.hasPerm(this) && (!it.mustByPlayer || this is Player) }
            .joinToString("\n") { it.usage }
        plugin.run {
            description.run {
                val author = if (authors.size == 1) authors.first()
                else authors.toString()
                sendMessage("&7-=[ &6$name V$version&b by $author &7]=-\n${help}".color())
            }
        }
    }
}