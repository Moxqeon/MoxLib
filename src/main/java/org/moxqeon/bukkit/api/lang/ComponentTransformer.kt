package org.moxqeon.bukkit.api.lang

import net.kyori.adventure.text.Component
import org.moxqeon.bukkit.api.context.LanguageContext

/**
 * 文本组件转换器接口。
 *
 * 用于对本地化后的 [Component] 做自定义转换处理，
 * 可结合语言环境上下文实现占位符替换、动态渲染等功能。
 */
interface ComponentTransformer {
    fun transform(component: Component, context: LanguageContext): Component

}