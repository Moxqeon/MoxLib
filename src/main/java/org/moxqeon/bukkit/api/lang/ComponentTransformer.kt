package org.moxqeon.bukkit.api.lang

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.moxqeon.bukkit.api.context.LanguageContext

interface ComponentTransformer {
    fun transform(component: TextComponent, context: LanguageContext): Component

}