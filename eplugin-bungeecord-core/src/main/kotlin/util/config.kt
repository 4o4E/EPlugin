@file:Suppress("UNUSED")

package top.e404.eplugin.bungeecord.util

import net.md_5.bungee.config.Configuration

fun Configuration.getBooleanOrNull(path: String) = get(path)?.let { it as? Boolean }
fun Configuration.getIntOrNull(path: String) = get(path)?.let { it as? Int }
fun Configuration.getDoubleOrNull(path: String) = get(path)?.let { it as? Double }
