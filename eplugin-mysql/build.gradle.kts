dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // mysql 8.0.30 5.1.49
    api("mysql:mysql-connector-java:8.0.30")
    // hikari
    api("com.zaxxer:HikariCP:4.0.3")
    // commons-lang3
    api("org.apache.commons:commons-lang3:3.12.0")
    // eplugin
    implementation(project(":eplugin-core"))
}