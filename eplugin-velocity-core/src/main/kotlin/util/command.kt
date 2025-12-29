package top.e404.eplugin.util

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player

val CommandSource.name: String get() = (this as? Player)?.username ?: "console"