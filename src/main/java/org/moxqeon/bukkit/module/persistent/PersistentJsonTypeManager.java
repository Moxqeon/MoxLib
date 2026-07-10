package org.moxqeon.bukkit.module.persistent;

import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.moxqeon.module.reflect.Reflect;
import org.moxqeon.bukkit.MoxBukkit;

import java.util.HashMap;
import java.util.Map;

public final class PersistentJsonTypeManager {
    private final Map<Class<?>, PersistentJsonType<?>> map = new HashMap<>();

    public void registerType(@NotNull PersistentJsonType<?> type) {
        map.put(type.getClass(), type);
    }

    public <T> PersistentJsonType<T> getType(@NotNull Class<? extends PersistentJsonType<T>> aClass) {
        return Reflect.cast(map.get(aClass));
    }

}

