@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
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
        if (actionBar.isNotBlank()) p
            .spigot()
            .sendMessage(ChatMessageType.ACTION_BAR, *TextComponent.fromLegacyText(actionBar.placeholder(*placeholder)))
        title?.display(p, *placeholder)
        sound?.playTo(p)
    }
    fun sendTo(p: Player, placeholder: (Player) -> Array<Pair<String, *>>) {
        sendTo(p, *placeholder(p))
    }

    fun sendTo(players: Iterable<Player>, vararg placeholder: Pair<String, *>) {
        players.forEach { sendTo(it, *placeholder) }
    }

    fun sendTo(players: Iterable<Player>, placeholder: (Player) -> Array<Pair<String, *>>) {
        players.forEach { sendTo(it, placeholder) }
    }

    fun sendToAll(vararg placeholder: Pair<String, *>) = sendTo(Bukkit.getOnlinePlayers(), *placeholder)
    fun sendToAll(placeholder: (Player) -> Array<Pair<String, *>>) = forEachOnline { sendTo(it, *placeholder(it)) }

    // with prefix
    fun sendTo(p: Player, prefix: String, vararg placeholder: Pair<String, *>) {
        if (chat.isNotBlank()) p.sendMessage(("&7[&f$prefix&7] $chat").placeholder(*placeholder))
        if (actionBar.isNotBlank()) p
            .spigot()
            .sendMessage(ChatMessageType.ACTION_BAR, *TextComponent.fromLegacyText(actionBar.placeholder(*placeholder)))
        title?.display(p, *placeholder)
        sound?.playTo(p)
    }
    fun sendTo(p: Player, prefix: String, placeholder: (Player) -> Array<Pair<String, *>>) {
        sendTo(p, prefix, *placeholder(p))
    }

    fun sendTo(players: Iterable<Player>, prefix: String, vararg placeholder: Pair<String, *>) {
        players.forEach { sendTo(it, prefix, *placeholder) }
    }

    fun sendTo(players: Iterable<Player>, prefix: String, placeholder: (Player) -> Array<Pair<String, *>>) {
        players.forEach { sendTo(it, prefix, placeholder) }
    }

    fun sendToAll(prefix: String, vararg placeholder: Pair<String, *>) = sendTo(Bukkit.getOnlinePlayers(), prefix, *placeholder)
    fun sendToAll(prefix: String, placeholder: (Player) -> Array<Pair<String, *>>) = forEachOnline { sendTo(it, prefix, *placeholder(it)) }
}