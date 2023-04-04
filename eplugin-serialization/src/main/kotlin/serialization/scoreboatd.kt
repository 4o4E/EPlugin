@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.Serializable
import top.e404.eplugin.EPlugin.Companion.placeholder
import top.e404.eplugin.scoreboard.Scoreboard

@Serializable
data class ScoreboardConfig(
    val title: String,
    val content: List<String>,
) {
    fun update(scoreboard: Scoreboard, vararg placeholder: Pair<String, *>) {
        scoreboard.title = title.placeholder(*placeholder)
        scoreboard.contents = content.map { it.placeholder(*placeholder) }
    }
}