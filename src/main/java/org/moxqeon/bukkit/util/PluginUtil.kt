package org.moxqeon.bukkit.util

import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.plugin.Plugin
import org.moxqeon.api.IOResult
import org.moxqeon.bukkit.api.Constructor
import org.moxqeon.util.FileUtil
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

object PluginUtil {
    @JvmStatic
    fun write(plugin: Plugin, resourcePath: String, file: File, charset: Charset): IOResult {
        try {
            plugin.getResource(resourcePath).use { input ->
                if (input == null) return IOResult.FAILURE_WITH_NO_EX
                return FileUtil.write(file, String(input.readAllBytes(), charset), charset)
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @JvmStatic
    fun write(plugin: Plugin, resourcePath: String, file: File): IOResult {
        return write(plugin, resourcePath, file, Charset.defaultCharset())
    }

    @JvmStatic
    fun getKey(plugin: Plugin, key: String, lenient: Boolean): NamespacedKey {
        return Constructor.namespacedKey(plugin.name.lowercase(Locale.getDefault()), key, lenient)
    }

    @JvmStatic
    fun getAdventureKey(plugin: Plugin, key: String, lenient: Boolean): Key {
        return Constructor.adventureKey(plugin.name.lowercase(Locale.getDefault()), key, lenient)
    }

    @JvmStatic
    fun getKey(plugin: Plugin, key: String): NamespacedKey {
        return getKey(plugin, key, false)
    }

    @JvmStatic
    fun getAdventureKey(plugin: Plugin, key: String): Key {
        return getAdventureKey(plugin, key, false)
    }

    @JvmStatic
    fun pluginOrVanillaKey(plugin: Plugin, key: String, lenient: Boolean): NamespacedKey {
        return if (key.startsWith("minecraft:")) NamespacedKey.fromString(key)!!
        else getKey(plugin, key, lenient)
    }

    @JvmStatic
    fun pluginOrVanillaKey(plugin: Plugin, key: String): NamespacedKey {
        return pluginOrVanillaKey(plugin, key, false)
    }
}

fun Plugin.write(resourcePath: String, file: File, charset: Charset) =
    PluginUtil.write(this, resourcePath, file, charset)

fun Plugin.write(resourcePath: String, file: File) = PluginUtil.write(this, resourcePath, file)