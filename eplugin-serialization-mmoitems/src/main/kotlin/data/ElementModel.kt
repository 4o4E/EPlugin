package top.e404.eplugin.serialization.mmoitems.data

import io.lumine.mythic.lib.element.Element
import io.lumine.mythic.lib.skill.handler.SkillHandler
import kotlinx.serialization.Serializable
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import top.e404.eplugin.reflect.getPrivateField

@Serializable
data class ElementModel(
    var id: String,
    var name: String,
    var loreIcon: String,
    var color: String,
    var icon: Material,
    var criticalStrikeName: String,
    var regularAttackName: String,
) {
    companion object {
        fun Element.toDataModel() = ElementModel(
            id = id,
            name = name,
            loreIcon = loreIcon,
            color = color,
            icon = icon,
            criticalStrikeName = getPrivateField<Element, SkillHandler<*>>("regularAttack").id,
            regularAttackName = getPrivateField<Element, SkillHandler<*>>("regularAttack").id,
        )
    }

    fun toItemData() = Element(YamlConfiguration().apply {
        set("id", id)
        set("name", name)
        set("lore-icon", loreIcon)
        set("color", color)
        set("icon", icon)
        set("crit-strike", criticalStrikeName)
        set("regular-attack", regularAttackName)
    })
}
