@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import org.bukkit.block.Biome
import top.e404.eplugin.EPlugin.Companion.formatAsConst

object BiomeSerialization : EnumSerialization<Biome>({ it.toBiome() })

fun String.toBiome() = Biome.valueOf(formatAsConst())
