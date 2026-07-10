package org.moxqeon.bukkit.api.context

import org.bukkit.plugin.Plugin
import org.moxqeon.api.context.Context

open class LanguageContext(val plugin: Plugin, val key: String) : Context by Context.EMPTY {
}