package org.moxqeon.bukkit.util

import org.bukkit.configuration.ConfigurationSection
import org.moxqeon.api.placeholder.Placeholder

object PlaceholderUtil {
    @JvmStatic
    fun getPlaceholder(config: ConfigurationSection, key: String): Placeholder<*> {
        return Placeholder.fromString(config.getString(key)!!)
    }
}