package org.moxqeon.platform;

import org.jetbrains.annotations.NotNull;

/**
 * 平台枚举。
 * <p>
 * 表示 MoxLib 当前运行的平台（Bukkit 或 Bungee）。
 */
public enum Platform {
    BUKKIT, BUNGEE;

    static Platform instance;

    public static void set(@NotNull Platform platform) {
        instance = platform;
    }

    @NotNull
    public static Platform get() {
        return instance;
    }

    boolean check(@NotNull Platform platform) {
        return instance == platform;
    }
}