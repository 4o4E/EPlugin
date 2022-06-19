package top.e404.testplugin

import top.e404.eplugin.EPlugin
import top.e404.testplugin.command.TestCommand
import top.e404.testplugin.config.TestConfig
import top.e404.testplugin.listener.TestListener

class TestPlugin : EPlugin() {
    override val debugPrefix = "TEST_PLUGIN_DEBUG"
    override val prefix = "TestPlugin"
    override fun enableDebug() = TestConfig.debug

    override fun onEnable() {
        INSTANCE = this
        TestConfig.load(null)
        TestCommand.register()
        TestListener.register()
        info("加载完成")
    }
}

lateinit var INSTANCE: TestPlugin
    private set