package org.moxqeon.bukkit.module.script;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.moxqeon.module.script.JavaScriptFunction;

public final class JavaScriptEventExecutor implements EventExecutor {
    @NotNull
    private final JavaScriptFunction function;

    @NotNull
    private final Class<? extends Event> eventClass;

    @NotNull
    private final EventPriority priority;

    private final boolean ignoreCancelled;

    public JavaScriptEventExecutor(@NotNull Class<? extends Event> eventClass, @NotNull JavaScriptFunction function, @NotNull EventPriority priority, boolean ignoreCancelled) {
        this.eventClass = eventClass;
        this.function = function;
        this.priority = priority;
        this.ignoreCancelled = ignoreCancelled;
    }

    public JavaScriptEventExecutor(@NotNull Class<? extends Event> eventClass, @NotNull JavaScriptFunction function, @NotNull EventPriority priority) {
        this(eventClass, function, priority, false);
    }

    public void execute(@NotNull Listener listener, @NotNull Event event) {
        this.function.invoke(new Object[]{event});
    }

    public void register(@NotNull Plugin plugin) {
        Bukkit.getPluginManager().registerEvent(this.eventClass, new Listener() {
        }, this.priority, this, plugin, this.ignoreCancelled);
    }
}
