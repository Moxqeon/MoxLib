package org.moxqeon.bukkit.api.lang

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.moxqeon.bukkit.api.context.LanguageContext
import org.moxqeon.module.script.JavaScriptFunction

class JSComponentTransformer(val function: JavaScriptFunction) : ComponentTransformer {
    override fun transform(
        component: TextComponent, context: LanguageContext
    ): Component = function.invoke<Component>(component, context)!!


}
