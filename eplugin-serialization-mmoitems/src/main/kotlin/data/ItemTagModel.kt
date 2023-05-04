package top.e404.eplugin.serialization.mmoitems.data

import io.lumine.mythic.lib.api.item.ItemTag
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemTagModel(
    var path: String,
    @SerialName("value_type") var valueType: String,
    var value: String,
) {
    companion object {
        fun ItemTag.toDataModel() = ItemTagModel(path, value.toType(), value.toString())
        private fun Any.toType() = when (this) {
            is Byte -> "byte"
            is Short -> "short"
            is Int -> "int"
            is Long -> "long"
            is Float -> "float"
            is Double -> "double"
            is Boolean -> "boolean"
            is String -> "string"
            else -> throw Exception("unknown type of ${this.javaClass.name}")
        }
    }

    private inline val actual
        get() = when (valueType) {
            "byte" -> value.toByte()
            "short" -> value.toShort()
            "int" -> value.toInt()
            "long" -> value.toLong()
            "float" -> value.toFloat()
            "double" -> value.toDouble()
            "boolean" -> value.toBoolean()
            else -> value
        }

    fun mmoitemsDeserialize() = ItemTag(path, actual)
}
