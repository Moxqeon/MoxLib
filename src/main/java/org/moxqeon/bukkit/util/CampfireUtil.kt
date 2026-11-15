package org.moxqeon.bukkit.util

import org.bukkit.block.Campfire

/**
 * 营火工具类。
 *
 * 用于判断营火是否正在烹饪。
 */
object CampfireUtil {
    @JvmStatic
    fun isCooking(campfire: Campfire): Boolean {
        for (i in 0..3) if (campfire.getItem(i) != null) return true
        return false
    }
}

@JvmSynthetic
fun Campfire.isCooking() = CampfireUtil.isCooking(this)