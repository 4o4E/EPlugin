package top.e404.eplugin.serialization.mmoitems.data

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import com.mojang.authlib.properties.PropertyMap
import kotlinx.serialization.Serializable
import top.e404.eplugin.config.serialization.UUIDSerialization
import top.e404.eplugin.reflect.getPrivateField
import top.e404.eplugin.reflect.setPrivateField
import java.util.*

@Serializable
data class GameProfileModel(
    @Serializable(UUIDSerialization::class) var id: UUID,
    var name: String,
    var properties: MutableMap<String, MutableCollection<@Serializable(PropertySerializer::class) Property>>,
    var legacy: Boolean,
) {
    companion object {
        fun GameProfile.toDataModel() = GameProfileModel(id, name, properties.asMap(), getPrivateField("legacy"))
    }

    fun mmoitemsDeserialize() = GameProfile(id, name).also {
        val p: PropertyMap = it.getPrivateField("properties")
        properties.forEach { (k, v) ->
            p.putAll(k, v)
        }
        it.setPrivateField("legacy", legacy)
    }
}
