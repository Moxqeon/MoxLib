package org.moxqeon.bukkit.util

import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.metadata.MetadataValue
import org.bukkit.metadata.Metadatable
import org.bukkit.plugin.Plugin
import org.moxqeon.bukkit.MoxBukkit

object MetadataUtil {
    @JvmStatic
    fun metaGet(metadatable: Metadatable, plugin: Plugin, key: String): MetadataValue? {
        for (value in metadatable.getMetadata(key)) {
            if (value.owningPlugin == plugin) return value
        }
        return null
    }

    @JvmStatic
    fun metaGet(metadatable: Metadatable, key: String): MetadataValue? {
        return metaGet(metadatable, MoxBukkit.instance(), key)
    }

    @JvmStatic
    fun metaSet(metadatable: Metadatable, plugin: Plugin, key: String, obj: Any) {
        metadatable.setMetadata(key, FixedMetadataValue(plugin, obj))
    }

    @JvmStatic
    fun metaSet(metadatable: Metadatable, key: String, obj: Any) {
        metaSet(metadatable, MoxBukkit.instance(), key, obj)
    }
}