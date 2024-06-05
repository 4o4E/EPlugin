@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin.Companion.placeholder
import top.e404.eplugin.util.forEachOnline

@Serializable
data class Message(
    val chat: String = "",
    @SerialName("action_bar") val actionBar: String = "",
    val title: Title? = null,
    val sound: SoundConfig? = null,
) {
    fun sendTo(p: Player, vararg placeholder: Pair<String, *>) {
        if (chat.isNotBlank()) p.sendMessage(chat.placeholder(*placeholder))
        @Suppress("DEPRECATION")
        if (actionBar.isNotBlank()) p.sendActionBar(actionBar.placeholder(*placeholder))
        title?.display(p, *placeholder)
        sound?.playTo(p)
    }

    fun sendTo(players: Iterable<Player>, vararg placeholder: Pair<String, *>) {
        players.forEach { sendTo(it, *placeholder) }
    }

    fun sendToAll(vararg placeholder: Pair<String, *>) = sendTo(Bukkit.getOnlinePlayers(), *placeholder)
    fun sendToAll(placeholder: (Player) -> Array<Pair<String, *>>) = forEachOnline { sendTo(it, *placeholder(it)) }
}