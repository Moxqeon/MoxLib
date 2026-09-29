package org.moxqeon.bukkit.api.lang

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.plugin.Plugin
import org.moxqeon.bukkit.api.context.LanguageContext
import org.moxqeon.bukkit.util.write
import org.moxqeon.util.FileUtil
import org.yaml.snakeyaml.Yaml
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.nio.charset.StandardCharsets
import java.util.concurrent.ConcurrentHashMap

object Language {
    private val yaml = Yaml()
    private val rawLanguageMap: ConcurrentHashMap<Plugin, MutableMap<String, String>> = ConcurrentHashMap()
    private val specialMap: ConcurrentHashMap<Plugin, MutableMap<String, ComponentTransformer>> = ConcurrentHashMap()

    @JvmStatic
    fun register(plugin: Plugin) {
        val langFile = FileUtil.subFile(plugin.dataFolder, "lang.yml")
        if (!langFile.exists()) plugin.write("lang.yml", langFile, StandardCharsets.UTF_8)
        BufferedInputStream(FileInputStream(langFile)).use {
            rawLanguageMap[plugin] = yaml.load(it)
        }
    }

    @JvmStatic
    fun registerSpecial(plugin: Plugin, key: String, transformer: ComponentTransformer) {
        if (localizeRaw(plugin, key) == null) return
        specialMap.computeIfAbsent(plugin) { ConcurrentHashMap() }[key] = transformer
    }

    @JvmStatic
    fun registerSpecial(plugin: Plugin, keys: Array<String>, transformer: ComponentTransformer) {
        for (key in keys)
            registerSpecial(plugin, key, transformer)
    }


    @JvmStatic
    fun unregister(plugin: Plugin) {
        rawLanguageMap.remove(plugin)
    }

    @JvmStatic
    fun localizeRaw(plugin: Plugin, key: String): String? {
        if (!rawLanguageMap.containsKey(plugin)) return null
        return rawLanguageMap[plugin]!![key]
    }

    @JvmStatic
    fun localize(plugin: Plugin, key: String, context: LanguageContext?): Component {
        if (!rawLanguageMap.containsKey(plugin)) return Component.text(key)
        val component = LegacyComponentSerializer.legacyAmpersand().deserialize(rawLanguageMap[plugin]!![key]!!)
        if (!specialMap.containsKey(plugin)) return component
        return if (!specialMap[plugin]!!.containsKey(key)) {
            component
        } else specialMap[plugin]!![key]!!.transform(component, context ?: LanguageContext(plugin, key))
    }
}

@JvmSynthetic
fun Plugin.localize(key: String, context: LanguageContext?) = Language.localize(this, key, context)

@JvmSynthetic
fun String.localize(plugin: Plugin, context: LanguageContext?) = Language.localize(plugin, this, context)