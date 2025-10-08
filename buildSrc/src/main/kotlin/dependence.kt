import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.spigot(configurationName: String = "compileOnly") = add(configurationName, "org.spigotmc:spigot-api:${Versions.spigot}")
fun DependencyHandler.mythicMobs(configurationName: String = "compileOnly") = add(configurationName,"io.lumine:Mythic-Dist:${Hooks.mythicmobs}")
fun DependencyHandler.mmoitems(configurationName: String = "compileOnly") = add(configurationName, "net.Indyuce:MMOItems-API:${Hooks.mmoitems}")
fun DependencyHandler.mmocore(configurationName: String = "compileOnly") = add(configurationName,"net.Indyuce:MMOCore-API:${Hooks.mmocore}")
fun DependencyHandler.mythicLib(configurationName: String = "compileOnly") = add(configurationName, "io.lumine:MythicLib-dist:${Hooks.mythicLib}")
fun DependencyHandler.worldeditCore(configurationName: String = "compileOnly") = add(configurationName, "com.sk89q.worldedit:worldedit-core:${Hooks.worldedit}")
fun DependencyHandler.worldeditBukkit(configurationName: String = "compileOnly") = add(configurationName, "com.sk89q.worldedit:worldedit-bukkit:${Hooks.worldedit}")
fun DependencyHandler.worldguard(configurationName: String = "compileOnly") = add(configurationName, "com.sk89q.worldguard:worldguard-bukkit:${Hooks.worldguard}")
fun DependencyHandler.itemsadder(configurationName: String = "compileOnly") = add(configurationName, "com.github.LoneDev6:api-itemsadder:${Hooks.itemsadder}")
fun DependencyHandler.modelEngine(configurationName: String = "compileOnly") = add(configurationName, "com.ticxo.modelengine:api:${Hooks.modelEngine}")

fun DependencyHandler.bstats(configurationName: String = "implementation", platform: String = "bukkit") = add(configurationName, "org.bstats:bstats-$platform:3.0.0")
fun DependencyHandler.serializationCore(configurationName: String = "compileOnly") = add(configurationName, kotlinx("serialization-core-jvm", Versions.serialization))
fun DependencyHandler.serializationJson(configurationName: String = "compileOnly") = add(configurationName, kotlinx("serialization-json", Versions.serialization))
fun DependencyHandler.kaml(configurationName: String = "compileOnly") = add(configurationName, "com.charleskorn.kaml:kaml:${Versions.kaml}")
fun DependencyHandler.bungeecord(configurationName: String = "compileOnly") = add(configurationName, "net.md-5:bungeecord-api:1.21-R0.3")

fun DependencyHandler.eplugin(module: String = "core", configurationName: String = "implementation") = add(configurationName, project(":eplugin-$module"))
fun DependencyHandler.epluginBungeecordCore(configurationName: String = "implementation") = eplugin("bungeecord-core", configurationName)
