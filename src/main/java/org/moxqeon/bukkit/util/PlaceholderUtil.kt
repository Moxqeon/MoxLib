package org.moxqeon.bukkit.util

import org.bukkit.configuration.ConfigurationSection
import org.moxqeon.api.placeholder.Placeholder

/**
 * 占位符工具类。
 *
 * 从配置节读取并解析占位符。
 */
object PlaceholderUtil {
    @JvmStatic
    fun getPlaceholder(config: ConfigurationSection, key: String): Placeholder<*> {
        return Placeholder.fromString(config.getString(key)!!)
    }
}