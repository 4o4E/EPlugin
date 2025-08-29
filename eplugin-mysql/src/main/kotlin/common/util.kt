package top.e404.eplugin.mysql.common

fun Any.sql() = toString().replace("'", "''")
