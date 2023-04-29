@file:Suppress("UNUSED")

package top.e404.eplugin.config

import org.bukkit.configuration.file.YamlConfiguration
import top.e404.eplugin.EPlugin
import java.io.File

class YamlMultiFileConfig(
    override val plugin: EPlugin,
    override val dirPath: String,
) : AbstractMultiFileConfig<YamlConfiguration>() {
    override fun loadFromSingleFile(file: File) = YamlConfiguration().also { it.load(file) }
    override fun saveToSingleFile(file: File, data: YamlConfiguration) = data.save(file)
}
