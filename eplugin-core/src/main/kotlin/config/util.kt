package top.e404.eplugin.config

fun List<Regex>.matches(string: String) = any { it.matches(string) }