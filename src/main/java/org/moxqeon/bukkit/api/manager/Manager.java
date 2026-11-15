package org.moxqeon.bukkit.api.manager;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

/**
 * 管理器接口。
 * <p>
 * 定义了基于命名空间键的注册与获取能力。
 *
 * @param <T> 管理对象的类型
 */
public interface Manager<T extends Keyed> {
    T get(@NotNull NamespacedKey key);

    T register(@NotNull T t);
}