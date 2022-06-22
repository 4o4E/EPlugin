@file:Suppress("UNUSED")

package top.e404.eplugin.update

import com.google.gson.JsonParser
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import top.e404.eplugin.EPlugin
import top.e404.eplugin.listener.EListener
import top.e404.eplugin.update.EUpdater.Ver.Companion.toVer
import java.net.URL

abstract class EUpdater(
    override val plugin: EPlugin,
    val url: String,
    val mcbbs: String,
    val github: String,
) : EListener(plugin) {
    companion object {
        private val jp = JsonParser()
    }

    var latest: Ver? = null
    lateinit var nowVer: Ver

    abstract fun enableUpdate(): Boolean

    // 返回最新的版本
    private fun queryLatest() = try {
        URL(url).readText()
            .let { jp.parse(it) }
            .asJsonArray[0]
            .asJsonObject["tag_name"]
            .asString!!
            .toVer()
    } catch (e: Exception) {
        plugin.warn("插件${plugin.name}-${plugin.description.version}检查更新时出现异常", e)
        null
    }

    fun init() {
        register()
        nowVer = plugin.description.version.toVer()
        if (!enableUpdate()) return
        plugin.runTaskTimerAsync(0, 20 * 60 * 60 * 6) {
            runCatching {
                latest = queryLatest()
                latest.let {
                    if (it == null) return@runCatching
                    if (it > nowVer) {
                        plugin.info(updateMessage)
                        return@runCatching
                    }
                    plugin.info("当前版本: &a${nowVer}&f已是最新版本")
                }
            }.onFailure {
                plugin.warn("检查版本更新时出现异常, 若需要手动更新请前往&b $mcbbs")
            }
        }
    }

    open val updateMessage: String
        get() = """&f插件有新版本, 当前版本: &c$nowVer&f, 最新版本: &a$latest
            |&f更新发布于:&b $mcbbs
            |&f开源于:&b $github
        """.trimMargin()

    @EventHandler
    fun onOpJoinGame(event: PlayerJoinEvent) = event.run {
        if (!player.isOp || !enableUpdate()) return@run
        latest.let {
            if (it != null
                && it > nowVer
            ) plugin.sendMsgWithPrefix(player, updateMessage)
        }
    }

    data class Ver(val major: Int, val minor: Int, val revision: Int) {
        companion object {
            fun String.toVer() = Ver(this)
            fun Ver(string: String) = string.split(".").run {
                Ver(get(0).toInt(), get(1).toInt(), get(2).toInt())
            }
        }

        override fun toString() = "$major.$minor.$revision"
        operator fun compareTo(v: Ver): Int {
            val f = major - v.major
            if (f != 0) return f
            val s = minor - v.minor
            if (s != 0) return s
            return revision - v.revision
        }
    }
}