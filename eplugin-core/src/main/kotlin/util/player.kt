@file:Suppress("UNUSED")

package top.e404.eplugin.util

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.util.Vector

fun OfflinePlayer.uuid() = uniqueId.toString()
fun OfflinePlayer.fixedUuid() = uniqueId.toString().replace("-", "")

fun getOnline() = Bukkit.getOnlinePlayers()
fun getOnlineOp() = getOnline().filter(Player::isOp)

inline fun forEachOp(block: (Player) -> Unit) = getOnlineOp().forEach(block)
inline fun forEachOnline(block: (Player) -> Unit) = getOnline().forEach(block)

private val vectors = listOf(
    Vector(0.3, -1.0, 0.3),
    Vector(-0.3, -1.0, 0.3),
    Vector(0.3, -1.0, -0.3),
    Vector(-0.3, -1.0, -0.3),
)

fun Player.getBlockUnderFoot() = vectors.map { location.add(it).block }

fun Player.isMidAir() = getBlockUnderFoot().all { it.isEmpty }

fun Player.playSound(sound: Sound) = playSound(location, sound, 1F, 1F)
