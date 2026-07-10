package org.moxqeon.util

import net.kyori.adventure.text.TextComponent

object MCListUtil {
    @JvmStatic
    fun toFormatted(strings: MutableList<String>): MutableList<String> {
        strings.replaceAll { string: String -> MCStringUtil.toFormatted(string) }
        return strings
    }

    @JvmStatic
    fun toRaw(strings: MutableList<String>, keepColorChar: Boolean): MutableList<String> {
        strings.replaceAll { str: String -> MCStringUtil.toRaw(str, keepColorChar) }
        return strings
    }

    @JvmStatic
    fun toRaw(strings: MutableList<String>): MutableList<String> {
        strings.replaceAll { string: String -> MCStringUtil.toRaw(string) }
        return strings
    }

//    @JvmStatic
//    fun toComponentList(strings: MutableList<String>): MutableList<TextComponent> {
//        val componentList = ArrayList<TextComponent>()
//        for (element in strings) componentList.add(MCStringUtil.toComponent(element))
//        return componentList
//    }
}

//fun MutableList<String>.toComponentList() = MCListUtil.toComponentList(this)
fun MutableList<String>.toFormatted() = MCListUtil.toFormatted(this)
fun MutableList<String>.toRaw() = MCListUtil.toRaw(this)
fun MutableList<String>.toRaw(keepColorChar: Boolean) = MCListUtil.toRaw(this, keepColorChar)