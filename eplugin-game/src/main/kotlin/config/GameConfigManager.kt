package top.e404.eplugin.game.config

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.bukkit.command.CommandSender
import top.e404.eplugin.EPlugin
import top.e404.eplugin.config.JarConfigDefault
import top.e404.eplugin.config.KtxConfig
import top.e404.eplugin.game.GameConfig

abstract class GameConfigManager<Game : GameConfig>(
    plugin: EPlugin,
    path: String,
    gameSerializer: KSerializer<Game>,
) : KtxConfig<List<Game>>(
    plugin,
    path,
    JarConfigDefault(plugin, path),
    ListSerializer(gameSerializer),
) {
    lateinit var data: Map<String, Game>
        private set

    override fun onLoad(config: List<Game>, sender: CommandSender?) {
        data = config.associateBy { it.info.name }
    }
}