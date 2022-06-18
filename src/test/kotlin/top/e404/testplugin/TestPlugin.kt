package top.e404.testplugin

import top.e404.eplugin.EPlugin
import top.e404.testplugin.command.TestCommand
import top.e404.testplugin.config.Config
import top.e404.testplugin.listener.TestListener

object TestPlugin : EPlugin() {
    override fun enableDebug() = true
    override val prefix = "TEST"
    override val debugPrefix = "TEST_DEBUG"
    override val bstatsId = 12345

    override fun onEnable() {
        INSTANCE = this
        Config.load(null)
        TestCommand.register()
        TestListener.register()
    }
}

lateinit var INSTANCE: TestPlugin
    private set