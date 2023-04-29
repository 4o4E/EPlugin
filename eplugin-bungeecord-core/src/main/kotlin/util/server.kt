@file:Suppress("UNUSED")

package top.e404.eplugin.bungeecord.util

import net.md_5.bungee.api.ProxyServer
import top.e404.eplugin.bungeecord.update.EUpdater
import java.util.regex.Pattern

val proxyVer by lazy {
    val p = Pattern.compile("(?<major>\\d+)\\.(?<minor>\\d+)(\\.(?<revision>\\d+))?-.*")
    val m = p.matcher(ProxyServer.getInstance().version)
    if (!m.find()) return@lazy null
    EUpdater.Ver(
        m.group("major").toInt(),
        m.group("minor").toInt(),
        m.group("revision")?.toIntOrNull() ?: 0
    )
}
