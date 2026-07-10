package org.moxqeon.bukkit.util

import net.kyori.adventure.text.Component


object AdventureUtil {
    @JvmStatic
    fun recursive(component: Component): MutableList<Component> {
        val result: MutableList<Component> = mutableListOf()
        result.add(component)
        for (child in component.children()) result.addAll(recursive(child))
        return result
    }
}