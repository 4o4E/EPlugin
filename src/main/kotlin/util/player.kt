@file:Suppress("UNUSED")

package top.e404.eplugin.util

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

fun OfflinePlayer.uuid() = uniqueId.toString()
fun OfflinePlayer.fixedUuid() = uniqueId.toString().replace("-", "")

fun getOnline() = Bukkit.getOnlinePlayers()
fun getOnlineOp() = getOnline().filter { it.isOp }

fun forEachOp(block: (Player) -> Unit) = getOnlineOp().forEach(block)
fun forEachOnline(block: (Player) -> Unit) = getOnline().forEach(block)