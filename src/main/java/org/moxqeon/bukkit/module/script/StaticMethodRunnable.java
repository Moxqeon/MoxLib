package org.moxqeon.bukkit.module.script;

import org.jetbrains.annotations.NotNull;
import org.moxqeon.api.Paramed;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class StaticMethodRunnable extends Paramed implements Runnable {
    @NotNull
    private final Method method;

    public StaticMethodRunnable(@NotNull Method method, @NotNull Object... params) {
        this.method = method;
        this.params = params;
    }

    @Override
    public void run() {
        try {
            method.invoke(null, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
