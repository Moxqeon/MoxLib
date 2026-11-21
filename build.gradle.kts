import java.net.URI
import java.nio.file.Files
import java.nio.file.StandardCopyOption

plugins {
    id("com.gradleup.shadow") version "8.3.11"
    id("java-library")
    id("maven-publish")
    kotlin("jvm")
    id("org.jetbrains.dokka") version "2.2.0"
    id("org.jetbrains.dokka-javadoc") version "2.2.0"
}

group = "com.github.Moxqeon"
version = "v1.1.0"

repositories {
    mavenLocal()
    maven {
        name = "Aliyun"
        url = URI("https://maven.aliyun.com/repository/public")
    }
    mavenCentral()
    maven {
        name = "Jitpack"
        url = URI("https://jitpack.io/")
    }
    maven {
        name = "CodeMC"
        url = URI("https://repo.codemc.io/repository/maven-public/")
    }
    maven {
        name = "Lumine"
        url = URI("https://mvn.lumine.io/repository/maven-public/")
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
    compileOnly("net.md-5:bungeecord-api:1.21-R0.4")
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("io.lumine:Mythic-Dist:5.7.1")
    compileOnly("net.kyori:adventure-api:4.14.0")
    compileOnly("net.kyori:adventure-text-minimessage:4.14.0")
    compileOnly("com.github.retrooper:packetevents-spigot:2.13.0")
    compileOnly("com.github.retrooper:packetevents-bungeecord:2.13.0")
    compileOnly("me.clip:placeholderapi:2.11.1")
    implementation("fr.mrmicky:fastboard:2.1.4")
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
    javadoc {
        dependsOn("dokkaGenerateJavadoc")
        enabled = false
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

dokka {
    dokkaSourceSets.configureEach {
        externalDocumentationLinks.register("MoxUtil") {
            url = URI("https://moxqeon.github.io/MoxUtil/javadoc")
            packageListUrl = URI("https://moxqeon.github.io/MoxUtil/javadoc/package-list")
        }
    }
    externalDocumentationLinks.register("java-se") {
        url.set("https://docs.oracle.com/en/java/javase/17/docs/api/")
        packageListUrl.set("https://docs.oracle.com/en/java/javase/17/docs/api/package-list")
    }
    externalDocumentationLinks.register("kotlin-stdlib") {
    url.set("https://kotlinlang.org/api/core/kotlin-stdlib/")
    // 先不写 packageListUrl，让 Dokka 自动解析
    }
    dokkaPublications {
        html {
            moduleName.set(project.name)
            moduleVersion.set(project.version.toString())
            outputDirectory.set(layout.buildDirectory.dir("dokka/html"))
        }
        javadoc {
            moduleName.set(project.name)
            moduleVersion.set(project.version.toString())
            outputDirectory.set(layout.buildDirectory.dir("docs/javadoc"))
        }
    }
}
