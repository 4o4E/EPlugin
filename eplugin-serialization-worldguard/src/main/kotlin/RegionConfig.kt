package top.e404.eplugin.config

interface RegionConfig<T> {
    val global: T
    val each: Map<String, T>
    val region: Map<String, T>
}