package top.e404.testplugin.config

import top.e404.eplugin.config.EConfig
import top.e404.testplugin.INSTANCE

object Config : EConfig(
    INSTANCE,
    "config.yml",
    Companion.JarConfig(INSTANCE, "config.yml"),
    false
) {

}