package top.e404.eplugin.mysql.common

import org.apache.commons.lang.StringEscapeUtils

fun Any.sql() = StringEscapeUtils.escapeSql(toString())!!
