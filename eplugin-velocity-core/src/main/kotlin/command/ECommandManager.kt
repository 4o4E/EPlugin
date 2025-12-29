@file:Suppress("UNUSED")

package top.e404.eplugin.command

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.command.SimpleCommand.Invocation
import com.velocitypowered.api.proxy.Player
import top.e404.eplugin.EPluginVelocity
import top.e404.eplugin.EPluginVelocity.Companion.component
import top.e404.eplugin.util.name

/**
 * 代表一个抽象的指令管理器
 *
 * @property plugin 插件实例
 * @property commands 指令列表
 * @since 1.0.0
 */
abstract class ECommandManager(
    val plugin: EPluginVelocity,
    val name: String,
    val alias: List<String>,
    val commands: MutableSet<ECommand>,
) : SimpleCommand {
    constructor(
        plugin: EPluginVelocity, name: String, alias: List<String>, vararg commands: ECommand
    ) : this(plugin, name, alias, commands.toMutableSet())

    fun register() = plugin.server.commandManager.run {
        val meta = metaBuilder(name).aliases(*alias.toTypedArray()).plugin(plugin).build()
        register(meta, this@ECommandManager)
        plugin.debug { "成功注册指令${name}" }
    }

    override fun execute(invocation: Invocation) {
        val sender = invocation.source()
        val args = invocation.arguments()
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
        try {
            c.onCommand(sender, args)
        } catch (e: FailException) {
            plugin.sendMsgWithPrefix(sender, e.message)
        }
        return
    }

    override fun suggest(invocation: Invocation): List<String> {
        val args = invocation.arguments()
        val sender = invocation.source()
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

    protected open fun CommandSource.sendHelp() {
        val help = commands
            .filter { it.hasPerm(this) && (!it.mustByPlayer || this is Player) }
            .joinToString("\n") { it.usage }
        plugin.run {
            container.description.run {
                val author =
                    if (authors.size == 1) authors.first()
                    else authors.toString()
                sendMessage("&7-=[ &6${name.get()} V${version.get()}&b by $author &7]=-\n${help}".component)
            }
        }
    }
}
