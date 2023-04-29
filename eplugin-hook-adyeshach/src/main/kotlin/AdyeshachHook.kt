package top.e404.eplugin.hook.ady

import ink.ptms.adyeshach.core.*
import ink.ptms.adyeshach.core.entity.manager.ManagerType
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

@Suppress("UNUSED")
open class AdyeshachHook(
    override val plugin: EPlugin,
) : EHook(plugin, "Adyeshach") {
    val api inline get() = Adyeshach.api()


    /**
     * 初始化玩家管理器
     */
    fun setupEntityManager(player: Player) = api.setupEntityManager(player)

    /**
     * 释放玩家管理器
     */
    fun releaseEntityManager(player: Player, async: Boolean = true) = api.releaseEntityManager(player, async)

    /**
     * 刷新玩家管理器（重新显示所有单位）
     */
    fun refreshEntityManager(player: Player) = api.refreshEntityManager(player)

    /**
     * 获取公共单位管理器
     *
     * @param type 容器类型
     */
    fun getPublicEntityManager(type: ManagerType = ManagerType.TEMPORARY) = api.getPublicEntityManager(type)

    /**
     * 获取私有单位管理器
     *
     * @param player 玩家
     * @param type 容器类型
     */
    fun getPrivateEntityManager(player: Player, type: ManagerType = ManagerType.TEMPORARY) = api.getPrivateEntityManager(player, type)

    /**
     * 获取用于检索实体的工具
     */
    fun getEntityFinder() = api.getEntityFinder()

    /**
     * 获取用于读取配置文件中的单位的序列化接口
     */
    fun getEntitySerializer() = api.getEntitySerializer()

    /**
     * 获取用于处理单位类型的接口
     */
    fun getEntityTypeRegistry() = api.getEntityTypeRegistry()

    /**
     * 获取用于处理单位元数据的接口
     */
    fun getEntityMetadataRegistry() = api.getEntityMetadataRegistry()

    /**
     * 获取用于处理单位控制器的接口
     */
    fun getEntityControllerRegistry() = api.getEntityControllerRegistry()

    /**
     * 获取 Hologram 控制接口
     */
    fun getHologramHandler() = api.getHologramHandler()

    /**
     * 获取 Kether 控制接口
     */
    fun getKetherHandler() = api.getKetherHandler()

    /**
     * 获取 Adyeshach 中的 NMS 接口
     */
    fun getMinecraftAPI() = api.getMinecraftAPI()

    /**
     * 获取 Adyeshach 中的网络接口
     */
    fun getNetworkAPI() = api.getNetworkAPI()

    /**
     * 获取 Adyeshach 中的语言文件接口
     */
    fun getLanguage() = api.getLanguage()

    /**
     * 获取事件总线
     */
    fun getEventBus() = api.getEventBus()
}
