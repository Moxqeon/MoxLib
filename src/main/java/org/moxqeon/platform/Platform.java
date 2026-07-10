package org.moxqeon.platform;

import org.jetbrains.annotations.NotNull;

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
