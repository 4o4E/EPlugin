package top.e404.eplugin.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerChatEvent
import top.e404.eplugin.EPlugin
import java.util.*

open class ETrapperManager(override val plugin: EPlugin) : EListener(plugin) {
    private val trappers = Collections.synchronizedSet(HashSet<EChatTrapper>())

    fun addTrapper(trapper: EChatTrapper) = trappers.add(trapper)
    fun cancelTrapper(trapper: EChatTrapper) = trappers.remove(trapper)

    @EventHandler
    fun AsyncPlayerChatEvent.onEvent() {
        for (trapper in trappers) {
            if (trapper.target != player) continue
            if (trapper.onChat(message)) trappers.remove(trapper)
            return
        }
    }
}

