package org.moxqeon.bukkit.util

import org.bukkit.block.Campfire

object CampfireUtil {
    @JvmStatic
    fun isCooking(campfire: Campfire): Boolean {
        for (i in 0..3) if (campfire.getItem(i) != null) return true
        return false
    }
}

@JvmSynthetic
fun Campfire.isCooking() = CampfireUtil.isCooking(this)