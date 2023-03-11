dependencies {
    // bungeecord
    implementation("net.md-5:bungeecord-api:1.16-R0.5-SNAPSHOT")
    // eplugin
    implementation(project(":eplugin-bungeecord-core"))
    // serialization
    compileOnly(kotlinx("serialization-core-jvm", Versions.serialization))
    compileOnly(kotlinx("serialization-json", Versions.serialization))
    // kaml
    compileOnly("com.charleskorn.kaml:kaml:${Versions.kaml}")
}