package org.moxqeon.bukkit.module.script;

import org.jetbrains.annotations.NotNull;
import org.moxqeon.api.Paramed;
import org.moxqeon.module.script.JavaScriptFunction;

public final class JavaScriptRunnable extends Paramed implements Runnable {
    @NotNull
    private final JavaScriptFunction function;

    public JavaScriptRunnable(@NotNull JavaScriptFunction function, @NotNull Object... params) {
        this.function = function;
        this.params = params;
    }

    @Override
    public void run() {
        function.invoke(params);
    }
}
