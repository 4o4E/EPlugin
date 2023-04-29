package top.e404.eplugin.config.serialization

import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor

inline fun <reified T> T.primitive(
    kind: PrimitiveKind = PrimitiveKind.STRING,
) = PrimitiveSerialDescriptor(T::class.java.name, kind)
