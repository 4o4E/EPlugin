package top.e404.eplugin.serialization.mmoitems.data

import com.mojang.authlib.properties.Property
import kotlinx.serialization.Serializable

@Serializable
data class PropertyModel(
    var name: String,
    var value: String,
    var signature: String,
) {
    companion object {
        fun Property.toDataModel() = PropertyModel(name, value, signature)
    }

    fun mmoitemsDeserialize() = Property(name, value, signature)
}
