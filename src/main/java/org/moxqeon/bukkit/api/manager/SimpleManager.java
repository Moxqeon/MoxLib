package org.moxqeon.bukkit.api.manager;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SimpleManager<T extends Keyed> implements Manager<T> {
    protected Map<NamespacedKey, T> registry = new ConcurrentHashMap<>();

    @Override
    public T get(@NotNull NamespacedKey key) {
        return registry.get(key);
    }

    @Override
    public T register(@NotNull T t) {
        registry.put(t.getKey(), t);
        return t;
    }
}
