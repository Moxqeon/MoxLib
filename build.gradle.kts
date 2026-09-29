import java.net.URI

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("java-library")
    id("maven-publish")
    kotlin("jvm") version "2.4.0"
}

group = "org.moxqeon"
version = "1.1.0"

repositories {
    mavenLocal() {
        metadataSources {
            artifact()   // 只要有 JAR 文件就行
        }
    }
    maven {
        name = "Aliyun"
        url = URI("https://maven.aliyun.com/repository/public")
    }
    mavenCentral()
    maven {
        name = "PaperMC"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "CodeMC"
        url = URI("https://repo.codemc.io/repository/maven-public/")
    }
    maven {
        name = "DMulloy"
        url = URI("https://repo.dmulloy2.net/repository/public/")
    }
    maven {
        name = "Minebench"
        url = URI("https://repo.minebench.de/")
    }
}

dependencies {
    api("de.tr7zw:item-nbt-api-plugin:2.10.0")
    api("org.moxqeon:MoxUtil:1.1.0")
    api("org.java-websocket:Java-WebSocket:1.5.7")
    compileOnly(fileTree(mapOf("dir" to "../../BukkitLib", "include" to "*.jar")))
    compileOnly("net.md-5:bungeecord-api:1.20-R0.2")
    compileOnly("com.github.retrooper:packetevents-spigot:2.13.0")
    compileOnly("com.github.retrooper:packetevents-bungeecord:2.13.0")
    compileOnly("me.clip:placeholderapi:2.11.1")
    implementation("fr.mrmicky:fastboard:2.1.4")
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
}

publishing {
    publications {
        create<MavenPublication>("bukkit") {
            from(components["java"])
        }
    }
}
kotlin {
    jvmToolchain(16)
}
