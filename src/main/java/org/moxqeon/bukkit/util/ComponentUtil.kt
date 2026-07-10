package org.moxqeon.bukkit.util

import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextDecoration

object ComponentUtil {
    @JvmStatic
    fun asString(textComponent: TextComponent): String {
        val builder = StringBuilder(textComponent.content())
        for (child in textComponent.children()) {
            val color = child.color()
            if (color != null) builder.append("<").append(color.asHexString()).append(">")
            if (child.hasDecoration(TextDecoration.OBFUSCATED)) builder.append("&k")
            if (child.hasDecoration(TextDecoration.BOLD)) builder.append("&l")
            if (child.hasDecoration(TextDecoration.STRIKETHROUGH)) builder.append("&m")
            if (child.hasDecoration(TextDecoration.UNDERLINED)) builder.append("&n")
            if (child.hasDecoration(TextDecoration.ITALIC)) builder.append("&o")
            builder.append((child as TextComponent).content()).append("&r")
        }
        return builder.toString()
    }
}

@JvmSynthetic
fun TextComponent.asString() = ComponentUtil.asString(this)