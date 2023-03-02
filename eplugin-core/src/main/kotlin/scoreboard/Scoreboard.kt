package top.e404.eplugin.scoreboard

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Scoreboard
import top.e404.eplugin.EPlugin.Companion.color

/**
 * 计分板，队伍实现
 */
@Suppress("UNUSED")
open class Scoreboard(
    title: String,
    val scoreboard: Scoreboard = Bukkit.getScoreboardManager()!!.newScoreboard,
) {
    companion object {
        private fun Int.asTeamName() = toString().map { "§$it" }.joinToString("", postfix = "§r")
        private fun String.toIndex() = removeSuffix("§r").replace("§", "").toIntOrNull()
    }

    private val objective = scoreboard.registerNewObjective(
        System.currentTimeMillis().toString(36), "dummy", title.color()
    ).also {
        it.displaySlot = DisplaySlot.SIDEBAR
    }

    open fun show(p: Player) {
        p.scoreboard = scoreboard
    }

    /**
     * 设置标题
     */
    open var title: String
        get() = objective.displayName
        set(value) {
            val title = value.color()
            if (objective.displayName != title) objective.displayName = title
        }

    /**
     * 设置内容
     */
    open var contents: List<String>
        get() = scoreboard.teams.map { it.prefix }
        set(value) {
            for ((i, content) in value.withIndex()) {
                val teamName = i.asTeamName()
                val team = scoreboard.getTeam(teamName)
                    ?: scoreboard.registerNewTeam(teamName).also { it.addEntry(teamName) }
                if (team.prefix != content) team.prefix = content
                objective.getScore(teamName).score = 0
            }
            for (team in scoreboard.teams) {
                val lid = team.name.toIndex() ?: continue
                if (value.size <= lid) {
                    scoreboard.resetScores(team.name)
                    team.unregister()
                }
            }
        }

    /**
     * 设置指定行内容
     */
    open operator fun set(index: Int, content: String) {
        val teamName = index.asTeamName()
        val team = scoreboard.getTeam(teamName)
            ?: scoreboard.registerNewTeam(teamName).also { it.addEntry(teamName) }
        if (team.prefix != content) team.prefix = content
        objective.getScore(teamName).score = index
    }

    /**
     * 销毁此计分板
     */
    fun destroy() = objective.unregister()
}