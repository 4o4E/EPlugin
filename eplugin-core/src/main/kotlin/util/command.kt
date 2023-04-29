@file:Suppress("UNUSED")

package top.e404.eplugin.util

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

fun String.execAsCommand(sender: CommandSender = Bukkit.getConsoleSender()) {
    Bukkit.dispatchCommand(sender, this)
}
