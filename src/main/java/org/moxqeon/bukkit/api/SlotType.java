package org.moxqeon.bukkit.api;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
        switch (this) {
            case MAINHAND:
                return EquipmentSlot.HAND;
            case OFFHAND:
                return EquipmentSlot.OFF_HAND;
            case HELMET:
                return EquipmentSlot.HEAD;
            case CHESTPLATE:
                return EquipmentSlot.CHEST;
            case LEGGINGS:
                return EquipmentSlot.LEGS;
            case BOOTS:
                return EquipmentSlot.FEET;
        }
        throw new IllegalArgumentException();
    }

    @NotNull
    public PlayerArmorChangeEvent.SlotType toPaper() {
        switch (this) {
            case HELMET:
                return PlayerArmorChangeEvent.SlotType.HEAD;
            case CHESTPLATE:
                return PlayerArmorChangeEvent.SlotType.CHEST;
            case LEGGINGS:
                return PlayerArmorChangeEvent.SlotType.LEGS;
            case BOOTS:
                return PlayerArmorChangeEvent.SlotType.FEET;
        }
        throw new IllegalArgumentException();
    }
}