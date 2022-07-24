package top.e404.eplugin.config

interface WorldConfig<T> {
    val global: T
    val world: Map<String, T>
}