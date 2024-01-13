package top.e404.eplugin.hook.playerparticles

import dev.esophose.playerparticles.PlayerParticles
import dev.esophose.playerparticles.api.PlayerParticlesAPI
import dev.esophose.playerparticles.event.ParticleStyleRegistrationEvent
import dev.esophose.playerparticles.styles.ParticleStyle
import org.bukkit.event.EventHandler
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook
import top.e404.eplugin.listener.EListener

@Suppress("UNUSED")
abstract class PlayerParticlesHook(
    final override val plugin: EPlugin,
) : EHook(plugin, "PlayerParticles") {
    private val listener = object : EListener(plugin) {
        @EventHandler
        fun onParticleStyleRegistration(event: ParticleStyleRegistrationEvent) {
            styles.forEach(event::registerStyle)
        }
    }

    override fun afterHook() = listener.register()

    abstract val styles: Collection<ParticleStyle>

    val api: PlayerParticlesAPI inline get() = PlayerParticlesAPI.getInstance()
    val hookPlugin: PlayerParticles inline get() = PlayerParticles.getInstance()
}