package org.moxqeon.bukkit.api.context

import org.bukkit.plugin.Plugin
import org.moxqeon.api.context.Context

/**
 * 语言环境上下文。
 *
 * 封装发起本地化的插件与语言键信息，用于本地化过程中的占位符替换等处理。
 *
 * @param plugin 发起本地化的插件
 * @param key 语言键
 */
open class LanguageContext(val plugin: Plugin, val key: String) : Context by Context.EMPTY {
}