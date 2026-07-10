package org.moxqeon.bukkit.util

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import org.bukkit.inventory.EquipmentSlot
import org.moxqeon.bukkit.api.SlotType

object SlotTypeUtil {
    @JvmStatic
    fun adapt(equipmentSlot: EquipmentSlot): SlotType {
        return when (equipmentSlot) {
            EquipmentSlot.HAND -> SlotType.MAINHAND
            EquipmentSlot.OFF_HAND -> SlotType.OFFHAND
            EquipmentSlot.HEAD -> SlotType.HELMET
            EquipmentSlot.CHEST -> SlotType.CHESTPLATE
            EquipmentSlot.LEGS -> SlotType.LEGGINGS
            EquipmentSlot.FEET -> SlotType.BOOTS
        }
    }

    @JvmStatic
    fun adapt(slotType: PlayerArmorChangeEvent.SlotType): SlotType {
        return adapt(EquipmentSlot.valueOf(slotType.toString()))
    }
}