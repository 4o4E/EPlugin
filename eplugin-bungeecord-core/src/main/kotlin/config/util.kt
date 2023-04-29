package top.e404.eplugin.bungeecord.config

fun List<Regex>.matches(string: String) = any { it.matches(string) }
