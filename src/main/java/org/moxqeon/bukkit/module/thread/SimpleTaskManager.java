package org.moxqeon.bukkit.module.thread;

import com.google.common.collect.Sets;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.*;

public abstract class SimpleTaskManager {
    protected final ThreadPoolExecutor executor;
    protected final Set<Future<?>> futures = Sets.newConcurrentHashSet();

    protected SimpleTaskManager(int size, int keepAlive) {
        executor = new ThreadPoolExecutor(size, size, keepAlive, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        executor.allowCoreThreadTimeOut(true);
    }

    protected SimpleTaskManager(@NotNull ConfigurationSection config) {
        this(config.getInt("Size"), config.getInt("KeepAlive"));
    }

    public void execute(@NotNull Runnable runnable) {
        futures.add(executor.submit(runnable));
    }

    public void execute(@NotNull Callable<?> callable) {
        futures.add(executor.submit(callable));
    }

    public void waitForDone() {
        for (Future<?> future : futures)
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        futures.clear();
    }


}
