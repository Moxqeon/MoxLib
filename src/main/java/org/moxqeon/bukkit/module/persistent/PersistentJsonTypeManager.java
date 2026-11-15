package org.moxqeon.bukkit.module.persistent;

import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.moxqeon.module.reflect.Reflect;
import org.moxqeon.bukkit.MoxBukkit;

import java.util.HashMap;
import java.util.Map;

/**
 * JSON 持久化数据类型管理器。
 * <p>
 * 负责注册与获取 JSON 持久化数据类型。
 */
public final class PersistentJsonTypeManager {
    private final Map<Class<?>, PersistentJsonType<?>> map = new HashMap<>();

    public void registerType(@NotNull PersistentJsonType<?> type) {
        map.put(type.getClass(), type);
    }

    public <T> PersistentJsonType<T> getType(@NotNull Class<? extends PersistentJsonType<T>> aClass) {
        return Reflect.cast(map.get(aClass));
    }

}