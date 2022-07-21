@file:Suppress("UNUSED")

package top.e404.eplugin.util

import org.bukkit.Bukkit
import top.e404.eplugin.update.EUpdater
import java.util.regex.Pattern

val mcVer by lazy {
    val p = Pattern.compile("(?<major>\\d+)\\.(?<minor>\\d+)(\\.(?<revision>\\d+))?-.*")
    val m = p.matcher(Bukkit.getBukkitVersion())
    if (!m.find()) return@lazy null
    EUpdater.Ver(
        m.group("major").toInt(),
        m.group("minor").toInt(),
        m.group("revision")?.toIntOrNull() ?: 0
    )
}