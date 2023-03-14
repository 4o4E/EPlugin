package top.e404.eplugin.config.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin.Companion.color
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
}