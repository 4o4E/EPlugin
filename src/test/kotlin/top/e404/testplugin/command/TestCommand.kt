package top.e404.testplugin.command

import top.e404.eplugin.command.AbstractCommandManager
import top.e404.testplugin.INSTANCE
import top.e404.testplugin.command.list.CommandReload

object TestCommand : AbstractCommandManager(
    INSTANCE,
    "test",
    CommandReload
)