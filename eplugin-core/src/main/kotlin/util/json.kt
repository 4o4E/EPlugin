@file:Suppress("UNUSED")

package top.e404.eplugin.util

import com.google.gson.Gson
import com.google.gson.JsonParser

val jsonParser = JsonParser()
val gson = Gson()

/**
 * 将字符串反序列化为JsonElement
 *
 * @return JsonElement
 */
fun String.asJson() =
    jsonParser.parse(this)!!

/**
 * 将对象序列化为Json字符串
 *
 * @return 字符串
 */
fun Any.toJson() =
    gson.toJson(this)!!
