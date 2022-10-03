@file:Suppress("UNUSED")

package top.e404.eplugin.bungeecord.command

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor
import top.e404.eplugin.bungeecord.EPlugin
import top.e404.eplugin.bungeecord.EPlugin.Companion.color

/**
 * 代表一个抽象的指令管理器
 *
 * @property plugin 插件实例
 * @property commands 指令列表
 * @since 1.0.0
 */
abstract class ECommandManager(
    val plugin: EPlugin,
    name: String,
    val commands: MutableSet<ECommand>,
) : Command(name), TabExecutor {
    constructor(
        plugin: EPlugin,
        name: String,
        vararg commands: ECommand
    ) : this(
        plugin,
        name,
        commands.toMutableSet()
    )

    fun register() {
        ProxyServer.getInstance().pluginManager.registerCommand(plugin, this)
        plugin.debug { "成功注册指令${name}" }
    }

    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (args.isEmpty()) {
            sender.sendHelp()
            return
        }
        val head = args[0].lowercase()
        if (head.equals("help", true)) {
            sender.sendHelp()
            return
        }
        val c = commands.firstOrNull { it.matchHead(head) }
        if (c == null) {
            plugin.sendUnknown(sender)
            return
        }
        // 无权限
        if (!c.hasPerm(sender)) {
            plugin.sendNoperm(sender)
            plugin.info(
                """玩家${sender.name}尝试使用指令${name} ${args.joinToString(" ")}
                    由于缺少权限${c.permission.joinToString(", ")}而被阻止
                """.trimIndent()
            )
            return
        }
        // 此指令只能由玩家执行 && 执行者不是玩家
        if (c.mustByPlayer && !plugin.isPlayer(sender)) return
        c.onCommand(sender, args)
        return
    }

    override fun onTabComplete(
        sender: CommandSender,
        args: Array<out String>
    ): MutableIterable<String> {
        val list = ArrayList<String>()
        val size = args.size // 最小只会为1
        val canUse = commands.filter { it.hasPerm(sender) }
        // 接管长度为1的指令的tab补全
        if (size == 1) return canUse.map { it.name }.toMutableList()
        // 其他长度
        val head = args[0]
        // 匹配指令头
        val c = canUse.firstOrNull { it.matchHead(head) }
        if (c == null || c.mustByPlayer && sender !is ProxiedPlayer) return list
        // 传递给指令
        c.onTabComplete(sender, args, list)
        val last = args.last()
        return list.filter { it.contains(last, true) }.toMutableList()
    }

    protected fun CommandSender.sendHelp() {
        val help = commands
            .filter { it.hasPerm(this) && (!it.mustByPlayer || this is ProxiedPlayer) }
            .joinToString("\n") { it.usage }
        plugin.run {
            description.run {
                sendMessage(TextComponent("&7-=[ &6$name V$version&b by $author &7]=-\n${help}".color()))
            }
        }
    }
}