package org.moxqeon.bukkit.api.context

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

/**
 * 玩家语言环境上下文。
 *
 * 继承自 [LanguageContext]，额外提供玩家的 UUID 作为占位符变量。
 */
class PlayerContext(plugin: Plugin, key: String, player: Player) : LanguageContext(plugin, key) {
    private val uuid = player.uniqueId
    override fun get(s: String): Any? {
        if (s.equals("uuid", ignoreCase = true)) return uuid
        return null
    }
}