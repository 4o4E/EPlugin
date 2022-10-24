package top.e404.eplugin.config

import kotlinx.serialization.Serializable
import top.e404.eplugin.config.serialization.RegexSerialization

/**
 * 对于方块的替换表
 *
 * @property input 匹配的方块类型的正则
 * @property output 用于替换匹配方块的方块类型
 */
@Serializable
data class ReplaceConfig(
    @Serializable(RegexSerialization::class)
    val input: Regex,
    val output: List<String>
)
