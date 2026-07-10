package org.moxqeon.bukkit.api;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public interface Manager<T extends Keyed> {
    T get(@NotNull NamespacedKey key);

    T register(@NotNull T t);
}
