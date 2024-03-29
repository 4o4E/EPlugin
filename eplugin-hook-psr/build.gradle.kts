repositories {
    jitpack()
}

dependencies {
    spigot()
    eplugin()
    // psr
    compileOnly("com.github.Rothes:ProtocolStringReplacer:2.11.1")
    // commons-collections
    compileOnly("commons-collections:commons-collections:3.2.2")
    // multiple-string-searcher
    compileOnly("org.neosearch.stringsearcher:multiple-string-searcher:0.1.1")
    // jetbrains annotations
    compileOnly("org.jetbrains:annotations:23.0.0")
}
