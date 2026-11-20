package org.moxqeon.bukkit.api.lang

import net.kyori.adventure.text.Component
import org.moxqeon.bukkit.api.context.LanguageContext
import org.moxqeon.module.script.JavaScriptFunction

/**
 * 基于 JavaScript 函数的文本组件转换器。
 *
 * 通过调用脚本中的 [JavaScriptFunction] 对文本组件进行转换，
 * 允许使用脚本动态处理本地化文本。
 */
class JSComponentTransformer(val function: JavaScriptFunction) : ComponentTransformer {
    override fun transform(
        component: Component, context: LanguageContext
    ): Component = function.invoke<Component>(component, context)!!


}