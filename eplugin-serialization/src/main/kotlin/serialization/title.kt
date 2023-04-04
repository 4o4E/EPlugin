@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin.Companion.color
import top.e404.eplugin.EPlugin.Companion.placeholder
import top.e404.eplugin.util.forEachOnline

@Serializable
data class Title(
    val title: String = "",
    val subtitle: String = "",
    @SerialName("fadein") val fadeIn: Int = 10,
    val stay: Int = 20,
    @SerialName("fadeout") val fadeOut: Int = 20,
) {
    fun show(p: Player) = p.sendTitle(title.color(), subtitle.color(), fadeIn, stay, fadeOut)
    fun showAll() = forEachOnline(::show)

    fun display(p: Player, vararg placeholder: Pair<String, *>) = p.sendTitle(
        title.placeholder(*placeholder),
        subtitle.placeholder(*placeholder),
        fadeIn,
        stay,
        fadeOut
    )

    fun displayAll(vararg placeholder: Pair<String, *>) = forEachOnline {
        it.sendTitle(
            title.placeholder(*placeholder),
            subtitle.placeholder(*placeholder),
            fadeIn,
            stay,
            fadeOut
        )
    }
}
