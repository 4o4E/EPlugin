@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import org.bukkit.Material
import top.e404.eplugin.EPlugin.Companion.formatAsConst

object MaterialSerializer : EnumSerialization<Material>({ Material.valueOf(it.formatAsConst()) })
