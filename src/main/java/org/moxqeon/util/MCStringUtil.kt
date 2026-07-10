package org.moxqeon.util

import com.google.common.base.Preconditions
import net.md_5.bungee.api.ChatColor
import kotlin.math.pow

object MCStringUtil {

    private val offsetMap = HashMap<Int?, String?>()

    private val offsetCache = HashMap<Int?, String?>()

    init {
        offsetMap[1] = "\uf821"
        offsetMap[2] = "\uf822"
        offsetMap[3] = "\uf823"
        offsetMap[4] = "\uf824"
        offsetMap[5] = "\uf825"
        offsetMap[6] = "\uf826"
        offsetMap[7] = "\uf827"
        offsetMap[8] = "\uf828"
        offsetMap[16] = "\uf829"
        offsetMap[32] = "\uf82a"
        offsetMap[64] = "\uf82b"
        offsetMap[128] = "\uf82c"
        offsetMap[256] = "\uf82d"
        offsetMap[512] = "\uf82e"
        offsetMap[1024] = "\uf82f"
        offsetMap[-1] = "\uf801"
        offsetMap[-2] = "\uf802"
        offsetMap[-3] = "\uf803"
        offsetMap[-4] = "\uf804"
        offsetMap[-5] = "\uf805"
        offsetMap[-6] = "\uf806"
        offsetMap[-7] = "\uf807"
        offsetMap[-8] = "\uf808"
        offsetMap[-16] = "\uf809"
        offsetMap[-32] = "\uf80a"
        offsetMap[-64] = "\uf80b"
        offsetMap[-128] = "\uf80c"
        offsetMap[-256] = "\uf80d"
        offsetMap[-512] = "\uf80e"
        offsetMap[-1024] = "\uf80f"
    }

    @JvmStatic
    fun getStandardGraphicalWidth(c: Char): Int {
        if (StringUtil.isChineseChar(c)) return 9
        if (c == 'I' || c == 't' || c == ' ') return 4
        if (c == 'f' || c == 'k') return 5
        if (c == 'i') return 2
        if (c == 'l') return 3
        if (c.toString().matches("\\w".toRegex())) return 6
        throw IllegalArgumentException()
    }

    @JvmStatic
    fun getStandardGraphicalWidth(string: String): Int {
        var result = 0
        for (c in string.toCharArray()) result += getStandardGraphicalWidth(c)
        return result
    }

    @JvmStatic
    fun toFormatted(string: String): String {
        var str = string
        str = ChatColor.translateAlternateColorCodes('&', str)
        val colorTags = StringUtil.find(str, "<#[0-9a-fA-F]+>")
        for (colorTag in colorTags) {
            if (colorTag.length == 9) str = str.replaceFirst(
                colorTag.toRegex(), ChatColor.of(colorTag.substring(1, colorTag.length - 1)).toString()
            )
        }
        return str
    }

    @JvmStatic
    fun toRaw(string: String, keepColorChar: Boolean): String {
        if (!keepColorChar) return string.replace("§[0-9a-fA-fk-oK-ORr]".toRegex(), "")
        return string.replace("§0".toRegex(), "&0").replace("§1".toRegex(), "&1").replace("§2".toRegex(), "&2")
            .replace("§3".toRegex(), "&3").replace("§4".toRegex(), "&4").replace("§5".toRegex(), "&5")
            .replace("§6".toRegex(), "&6").replace("§7".toRegex(), "&7").replace("§8".toRegex(), "&8")
            .replace("§9".toRegex(), "&9").replace("§[Aa]".toRegex(), "&a").replace("§[Bb]".toRegex(), "&b")
            .replace("§[Cc]".toRegex(), "&c").replace("§[Dd]".toRegex(), "&d").replace("§[Ee]".toRegex(), "&e")
            .replace("§[Ff]".toRegex(), "&f").replace("§[Kk]".toRegex(), "&k").replace("§[Ll]".toRegex(), "&l")
            .replace("§[Mm]".toRegex(), "&m").replace("§[Nn]".toRegex(), "&n").replace("§[Oo]".toRegex(), "&o")
            .replace("§[Rr]".toRegex(), "&r").replace("§[Xx]".toRegex(), "&x")
    }

    @JvmStatic
    fun toRaw(string: String): String {
        return toRaw(string, false)
    }

    fun applyOffset(string: String, offset: Int): String {
        return getOffset(offset) + string
    }


    private fun getOffset(offset: Int): String {
        var offset = offset
        if (offsetMap.containsKey(offset)) return offsetMap.get(offset)!!
        if (offsetCache.containsKey(offset)) return offsetCache.get(offset)!!
        else {
            val offsetString = StringBuilder()
            Preconditions.checkArgument(offset != 0)
            val offsetMeta = IntArray(7)
            if (offset > 0) {
                var i = 7
                while (i > 0) {
                    i--
                    val tmp = 2.0.pow((i + 4).toDouble()).toInt()
                    offsetMeta[i] = offset / tmp
                    offset -= offsetMeta[i] * tmp
                    for (count in 0..<offsetMeta[i]) {
                        offsetString.append(offsetMap.get(tmp))
                    }
                }
                if (offset > 8) {
                    offset -= 8
                    offsetString.append(offsetMap.get(8))
                }
                offsetString.append(offsetMap.get(offset))
            } else {
                var i = 7
                while (i > 0) {
                    i--
                    val tmp = 2.0.pow((i + 4).toDouble()).toInt() * -1
                    offsetMeta[i] = offset / tmp
                    offset -= offsetMeta[i] * tmp
                    for (count in 0..<offsetMeta[i]) offsetString.append(offsetMap.get(tmp))
                }
                if (offset < -8) {
                    offset += 8
                    offsetString.append(offsetMap.get(-8))
                }
                offsetString.append(offsetMap.get(offset))
            }
            offsetCache[offset] = offsetString.toString()
            return offsetString.toString()
        }
    }
}

fun String.toFormatted() = MCStringUtil.toFormatted(this)
fun String.toRaw() = MCStringUtil.toRaw(this)
fun String.toRaw(keepColorChar: Boolean) = MCStringUtil.toRaw(this, keepColorChar)
fun String.applyOffset(offset: Int) = MCStringUtil.applyOffset(this, offset)
fun String.graphicalWidth() = MCStringUtil.getStandardGraphicalWidth(this)

