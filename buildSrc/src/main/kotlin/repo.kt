import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.lumine() = maven("https://mvn.lumine.io/repository/maven-public/")
fun RepositoryHandler.phoenix() = maven("https://nexus.phoenixdevt.fr/repository/maven-public/")
fun RepositoryHandler.engineHub() = maven("https://maven.enginehub.org/repo/")
fun RepositoryHandler.jitpack() = maven("https://jitpack.io")
fun RepositoryHandler.taboo() = maven("https://repo.tabooproject.org/repository/releases/")
fun RepositoryHandler.codemc() = maven("https://repo.codemc.org/repository/maven-public/")
