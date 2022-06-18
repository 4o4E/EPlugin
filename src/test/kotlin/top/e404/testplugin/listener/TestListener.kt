package top.e404.testplugin.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import top.e404.eplugin.listener.EListener
import top.e404.testplugin.INSTANCE

object TestListener : EListener(INSTANCE) {
    @EventHandler
    fun PlayerJoinEvent.onEvent() {
        INSTANCE.sendMsgWithPrefix(player, "&bAWA")
    }
}