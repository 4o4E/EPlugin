@file:Suppress("UNUSED")

package top.e404.eplugin.bungeecord.util

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer

fun ProxiedPlayer.uuid() = uniqueId.toString()
fun ProxiedPlayer.fixedUuid() = uniqueId.toString().replace("-", "")

fun getOnline() = ProxyServer.getInstance().players
fun forEachOnline(block: (ProxiedPlayer) -> Unit) = getOnline().forEach(block)
