package org.moxqeon.bukkit.module.script;

import org.jetbrains.annotations.NotNull;
import org.moxqeon.api.Paramed;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 静态方法可运行任务。
 * <p>
 * 在线程中通过反射调用静态方法并携带参数。
 */
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