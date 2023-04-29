@file:Suppress("UNUSED")

package top.e404.eplugin.config

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import top.e404.eplugin.EPlugin
import top.e404.eplugin.config.KtxConfig.Companion.defaultYaml
import java.io.File

open class KtxMultiFileConfig<T : Any>(
    override val plugin: EPlugin,
    override val dirPath: String,
    val serializer: KSerializer<T>,
    val format: StringFormat = defaultYaml
) : AbstractMultiFileConfig<T>() {
    override fun loadFromSingleFile(file: File) = format.decodeFromString(serializer, file.readText())
    override fun saveToSingleFile(file: File, data: T) = file.writeText(format.encodeToString(serializer, data))
}
