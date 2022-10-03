package top.e404.eplugin.bungeecord.listener

import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.event.EventHandler
import net.md_5.bungee.event.EventPriority
import top.e404.eplugin.bungeecord.EPlugin
import java.util.*

open class ETrapperManager(override val plugin: EPlugin) : EListener(plugin) {
    private val trappers = Collections.synchronizedSet(HashSet<EChatTrapper>())

    fun addTrapper(trapper: EChatTrapper) = trappers.add(trapper)
    fun cancelTrapper(trapper: EChatTrapper) = trappers.remove(trapper)
    fun hasTrapper(p: ProxiedPlayer) = trappers.any { it.target == p }

    @EventHandler(priority = EventPriority.LOWEST)
    fun ChatEvent.onEvent() {
        for (trapper in trappers) {
            if (trapper.target != sender) continue
            isCancelled = true
            if (trapper.onChat(message)) trappers.remove(trapper)
            return
        }
    }
}

