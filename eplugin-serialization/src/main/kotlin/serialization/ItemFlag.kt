@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import org.bukkit.inventory.ItemFlag
import top.e404.eplugin.EPlugin.Companion.formatAsConst

object ItemFlagSerializer : EnumSerialization<ItemFlag>({ ItemFlag.valueOf(it.formatAsConst()) })