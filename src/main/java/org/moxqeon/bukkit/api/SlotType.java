package org.moxqeon.bukkit.api;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 装备槽位类型枚举。
 * <p>
 * 表示主手、副手以及四件护甲槽位，并提供与 Bukkit 装备槽位的转换。
 */
public enum SlotType {
    MAINHAND, OFFHAND, HELMET, CHESTPLATE, LEGGINGS, BOOTS;

    public static final List<SlotType> BODY;

    public static final List<SlotType> HAND;

    static {
        BODY = List.of(HELMET, CHESTPLATE, LEGGINGS, BOOTS);
        HAND = List.of(MAINHAND, OFFHAND);
    }

    @NotNull
    public EquipmentSlot toBukkit() {
        return switch (this) {
            case MAINHAND -> EquipmentSlot.HAND;
            case OFFHAND -> EquipmentSlot.OFF_HAND;
            case HELMET -> EquipmentSlot.HEAD;
            case CHESTPLATE -> EquipmentSlot.CHEST;
            case LEGGINGS -> EquipmentSlot.LEGS;
            case BOOTS -> EquipmentSlot.FEET;
        };
    }

//    @NotNull
//    public PlayerArmorChangeEvent.SlotType toPaper() {
//        return switch (this) {
//            case HELMET -> PlayerArmorChangeEvent.SlotType.HEAD;
//            case CHESTPLATE -> PlayerArmorChangeEvent.SlotType.CHEST;
//            case LEGGINGS -> PlayerArmorChangeEvent.SlotType.LEGS;
//            case BOOTS -> PlayerArmorChangeEvent.SlotType.FEET;
//            default -> throw new IllegalArgumentException();
//        };
//    }
}