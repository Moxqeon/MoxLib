package org.moxqeon.bukkit.util

import org.bukkit.event.entity.FoodLevelChangeEvent
import org.moxqeon.bukkit.api.FoodLevelChangeType

object FoodLevelUtil {
    @JvmStatic
    fun foodLevelChangeType(event: FoodLevelChangeEvent): FoodLevelChangeType {
        return if (event.foodLevel < event.getEntity().foodLevel) FoodLevelChangeType.DOWN else FoodLevelChangeType.UP
    }
}