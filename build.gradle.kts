import java.net.URI
import java.nio.file.Files
import java.nio.file.StandardCopyOption

plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.11"
    id("java-library")
    id("maven-publish")
    kotlin("jvm")
}

group = "com.github.Moxqeon"
version = "v1.1.0"

repositories {
    mavenLocal()
    maven {
        name = "Aliyun"
        url = URI("https://maven.aliyun.com/repository/public")
    }
    maven {
        name = "Jitpack"
        url = URI("https://jitpack.io/")
    }
    mavenCentral()
    maven {
        name = "CodeMC"
        url = URI("https://repo.codemc.io/repository/maven-public/")
    }
    maven {
        name = "Minebench"
        url = URI("https://repo.minebench.de/")
    }
}

dependencies {
    api("de.tr7zw:item-nbt-api-plugin:2.12.3")
    api("com.github.Moxqeon:MoxUtil:v1.1.0")
    api("org.java-websocket:Java-WebSocket:1.5.7")

//    compileOnly(fileTree(mapOf("dir" to "../../BukkitLib", "include" to "*.jar")))
    compileOnly("net.md-5:BungeeCord:2104")
    compileOnly("io.papermc:paper-api:1.20.4")
    compileOnly("io.papermc:paper-core:1.20.4")
    compileOnly("io.lumine:MythicMobs:5.7.1")
    compileOnly("net.kyori:adventure-api:4.13.1")
    compileOnly("net.kyori:adventure-text-minimessage:4.13.1")
    compileOnly("com.github.retrooper:packetevents-spigot:2.13.0")
    compileOnly("com.github.retrooper:packetevents-bungeecord:2.13.0")
    compileOnly("me.clip:placeholderapi:2.11.1")
    implementation("fr.mrmicky:fastboard:2.1.4")
//    implementation("org.openjdk.nashorn:nashorn-core:15.4")
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    processResources {
        filteringCharset = "UTF-8"
        val props = mapOf("version" to version)
        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
        filesMatching("bungee.yml") {
            expand(props)
        }
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    shadowJar {
        doLast {
            val jarFile = this@shadowJar.archiveFile.get().asFile
            val dir = File("C:\\Users\\Kciyamat\\Desktop\\Purpur\\plugins")
            val dest = File(dir, "${jarFile.name}")
            dir.mkdirs()
            Files.copy(jarFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("bukkit") {
            from(components["java"])
        }
    }
}
kotlin {
    jvmToolchain(21)
}
