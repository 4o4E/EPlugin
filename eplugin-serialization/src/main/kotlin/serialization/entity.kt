package top.e404.eplugin.config.serialization

import org.bukkit.entity.EntityType
import top.e404.eplugin.EPlugin.Companion.formatAsConst

@Suppress("UNUSED")
object EntityTypeSerialization : EnumSerialization<EntityType>({
    val name = it.formatAsConst()
    @Suppress("DEPRECATION")
    EntityType.entries.first { type ->
        type.name.equals(name, true) || type.getName().equals(name, true)
    }
})