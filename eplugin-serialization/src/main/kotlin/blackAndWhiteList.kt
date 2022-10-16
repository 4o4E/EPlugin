@file:Suppress("UNUSED")

package top.e404.eplugin.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import top.e404.eplugin.config.serialization.RegexSerialization

interface BwList {
    /**
     * 是否为黑名单, 若为黑名单则不匹配黑名单中的内容, 反之为白名单, 仅匹配白名单中的内容
     */
    val isBlack: Boolean

    /**
     * 确认内容是否匹配此名单
     *
     * @param content 内容
     * @return 若为黑名单则返回`文本是否不在名单中`, 若为白名单则返回`文本是否在名单中`
     */
    fun matches(content: String): Boolean
}

/**
 * 代表一个可以设置的黑白名单
 *
 * @property texts 文本列表
 */
@Serializable
open class StringBwList(
    @SerialName("is_black")
    override val isBlack: Boolean,
    val texts: List<String>
) : BwList {
    override fun matches(content: String) =
        if (isBlack) content !in texts
        else content in texts
}

/**
 * 代表一个可以设置的黑白名单
 *
 * @property isBlack 是否为黑名单
 * @property regexes 正则列表
 */
@Serializable
open class RegexBwList(
    @SerialName("is_black")
    override val isBlack: Boolean,
    val regexes: List<@Serializable(RegexSerialization::class) Regex>
) : BwList {
    override fun matches(content: String) =
        if (isBlack) !regexes.matches(content)
        else regexes.matches(content)
}
