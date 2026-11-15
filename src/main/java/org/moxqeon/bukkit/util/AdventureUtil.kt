package org.moxqeon.bukkit.util

import net.kyori.adventure.text.Component


/**
 * Adventure 组件工具类。
 *
 * 提供递归遍历组件子节点的辅助方法。
 */
object AdventureUtil {
    @JvmStatic
    fun recursive(component: Component): MutableList<Component> {
        val result: MutableList<Component> = mutableListOf()
        result.add(component)
        for (child in component.children()) result.addAll(recursive(child))
        return result
    }
}