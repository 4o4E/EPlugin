@file:Suppress("UNUSED")

package top.e404.eplugin.game

import top.e404.dynamicmap.config.DynamicMapConfigManager
import top.e404.dynamicmap.dyn.DynamicMap
import top.e404.dynamicmap.dyn.DynamicMapManager
import top.e404.eplugin.EPlugin
import top.e404.eplugin.config.serialization.ELocation
import top.e404.eplugin.game.config.GameConfigManager

/**
 * 游戏管理器, 包含多种地图
 *
 * @param GamePlayer 玩家信息
 * @property plugin 对应的插件
 * @property dynamicMapManager 动态地图管理器
 * @property dynamicMapConfigManager 动态地图配置管理器
 */
abstract class GameManager<Config : GameConfig, GamePlayer : Gamer>(
    val plugin: EPlugin,
    val gameConfigManager: GameConfigManager<Config>,
    val dynamicMapManager: DynamicMapManager,
    val dynamicMapConfigManager: DynamicMapConfigManager,
) {
    /**
     * 游戏名字
     */
    abstract val gameName: String

    abstract val lobby: ELocation

    /**
     * 房间原型
     */
    val protoRooms: Map<String, Config> get() = gameConfigManager.data

    /**
     * 启用了的地图实例
     */
    val instances = mutableListOf<GameInstance<Config, GamePlayer>>()

    fun getDynamicMap(name: String): DynamicMap {
        val dynamicMapConfig = dynamicMapConfigManager.data[name] ?: error("未知的动态地图: $name")
        return dynamicMapManager.create(gameName, dynamicMapConfig)
    }

    abstract fun newInstance(config: GameConfig): GameInstance<Config, GamePlayer>
}