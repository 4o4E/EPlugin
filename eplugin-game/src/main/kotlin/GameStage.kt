package top.e404.eplugin.game

import kotlinx.serialization.Serializable

@Serializable
@Suppress("UNUSED")
enum class GameStage(val message: String, val allowJoin: Boolean) {
    /**
     * 初始化游戏, 准备房间等资源
     */
    INITIAL("初始化", false),

    /**
     * 等待玩家加入
     */
    WAITING("等待玩家", true),

    /**
     * 开始倒计时, 倒计时结束后进入游戏阶段, 若玩家数量不足则返回等待玩家阶段
     */
    READY("倒计时", true),

    /**
     * 游戏中
     */
    GAMING("游戏中", false),

    /**
     * 游戏结算, 该阶段退出的玩家才统计其游戏记录
     */
    ENDING("结算", false);
}