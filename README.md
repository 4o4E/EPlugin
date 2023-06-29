# [EPlugin](https://github.com/4o4E/EPlugin)

> `spigot`-`kotlin` 插件框架
> 
> 按照功能区分模块, 支持按需引入

若模块没有声明则默认使用`SpigotAPI-1.13.2`作为依赖(在这之前的版本没有非空注解, 和kotlin相性不佳)

## 开始使用

1. `git clone https://github.com/4o4E/EPlugin.git`
2. `gradlew publishToMavenLocal`

```kotlin
val epluginVer = "1.0.5" // 在release中查看最新版本
fun eplugin(module: String, version: String = epluginVer) = "top.e404:eplugin-$module:$version"

repositories {
    mavenLocal()
}

dependencies {
    // eplugin core
    implementation(eplugin("core"))
    // 序列化模块(可选)
    implementation(eplugin("serialization"))
    // papi依赖(可选|papi依赖需要手动导入)
    implementation(eplugin("hook-placeholderapi"))
    // placeholderAPI 手动导入papi依赖
    compileOnly("me.clip:placeholderapi:2.11.1")
}

tasks {
    shadowJar {
        archiveFileName.set("${project.name}-${project.version}.jar")
        // 推荐对kotlin和eplugin进行relocate, 避免不同版本的依赖互相冲突
        relocate("kotlin", "top.e404.eclean.relocate.kotlin")
        relocate("top.e404.eplugin", "top.e404.eclean.relocate.eplugin")
    }
}
```

## 使用`EPlugin`的实际项目

- [EClean](https://github.com/4o4E/EClean)
- [Boom](https://github.com/4o4E/Boom)

## 项目结构

- [eplugin-bungeecord-core](eplugin-bungeecord-core) - 通用Bungeecord插件
- [eplugin-bungeecord-serialization](eplugin-bungeecord-serialization) - Bungeecord序列化相关
- [eplugin-channel](eplugin-channel) - Bungeecord通信频道相关
- [eplugin-core](eplugin-core) - 通用Spigot插件核心
- `eplugin-hook-xxx` - xxx插件依赖的封装
- [eplugin-menu](eplugin-menu) - 菜单相关
- [eplugin-mysql](eplugin-mysql) - `mysql`, `hikari`封装
- [eplugin-reflect](eplugin-reflect) - 反射库
- [eplugin-serialization](eplugin-serialization) - kotlinx.serialization配置文件实现及常用属性的序列化器
- [eplugin-serialization-mmoitems](eplugin-serialization-mmoitems) - mmoitems实体类(`RandomStatData`和`StatData`)的序列化
- [eplugin-serialization-multitem](eplugin-serialization-multitem) - `mmoitems`, `mythicmobs`, `itemsadder`物品统一抽象模型
- [eplugin-serialization-particle](eplugin-serialization-particle) - 粒子序列化模型
- [eplugin-serialization-worldedit](eplugin-serialization-worldedit) - 替换表实体类
- [eplugin-serialization-worldguard](eplugin-serialization-worldguard) - 简单的 region-world-global 配置模板
