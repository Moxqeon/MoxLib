package org.moxqeon.bukkit.api

import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.moxqeon.util.StringUtil

/**
 * 键构造工具类。
 *
 * 用于便捷地创建 Bukkit 的 [NamespacedKey] 与 Adventure 的 [Key]，
 * 支持可选地将命名空间与键转换为蛇形命名。
 */
object Constructor {
    @JvmStatic
    fun namespacedKey(namespace: String, key: String, lenient: Boolean): NamespacedKey {
        return NamespacedKey.fromString(
            if (lenient) (StringUtil.camelToSnake(namespace) + ":" + StringUtil.camelToSnake(key))
            else ("$namespace:$key")
        )!!
    }

    @JvmStatic
    fun namespacedKey(namespace: String, key: String): NamespacedKey {
        return namespacedKey(namespace, key, false)
    }

    @JvmStatic
    fun adventureKey(namespace: String, key: String, lenient: Boolean): Key {
        var namespace = namespace
        var key = key
        namespace = if (lenient) StringUtil.camelToSnake(namespace) else namespace
        key = if (lenient) StringUtil.camelToSnake(key) else key
        return Key.key(namespace, key)
    }

    @JvmStatic
    fun adventureKey(namespace: String, key: String): Key {
        return adventureKey(namespace, key, false)
    }
}