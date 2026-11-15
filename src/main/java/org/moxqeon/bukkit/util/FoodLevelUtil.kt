package org.moxqeon.bukkit.util

import org.bukkit.event.entity.FoodLevelChangeEvent
import org.moxqeon.bukkit.api.FoodLevelChangeType

/**
 * 饥饿值工具类。
 *
 * 用于判断饥饿值变化事件的增减方向。
 */
object FoodLevelUtil {
    @JvmStatic
    fun foodLevelChangeType(event: FoodLevelChangeEvent): FoodLevelChangeType {
        return if (event.foodLevel < event.getEntity().foodLevel) FoodLevelChangeType.DOWN else FoodLevelChangeType.UP
    }
}