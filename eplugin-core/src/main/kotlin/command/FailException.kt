package top.e404.eplugin.command

@DslMarker
annotation class EPluginDsl

class FailException(override val message: String) : Exception()