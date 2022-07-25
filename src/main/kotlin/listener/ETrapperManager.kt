package top.e404.eplugin.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerChatEvent
import top.e404.eplugin.EPlugin
import java.util.*

open class ETrapperManager(override val plugin: EPlugin) : EListener(plugin) {
    private val trappers = Collections.synchronizedSet(HashSet<EChatTrapper>())

    fun addTrapper(trapper: EChatTrapper) = trappers.add(trapper)
    fun cancelTrapper(trapper: EChatTrapper) = trappers.remove(trapper)
    fun hasTrapper(p: Player) = trappers.any { it.target == p }

    @EventHandler
    fun AsyncPlayerChatEvent.onEvent() {
        for (trapper in trappers) {
            if (trapper.target != player) continue
            isCancelled = true
            if (trapper.onChat(message)) trappers.remove(trapper)
            return
        }
    }
}

