package org.moxqeon.bukkit;

import com.github.retrooper.packetevents.PacketEvents;
import com.google.common.base.Preconditions;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.moxqeon.MoxLib;
import org.moxqeon.bukkit.fix.OnGroundFix;
import org.moxqeon.bukkit.listener.InventoryClickListener;
import org.moxqeon.bukkit.listener.ItemPickUpListener;
import org.moxqeon.bukkit.listener.ScoreboardListener;
import org.moxqeon.bukkit.module.damage.DamageManager;
import org.moxqeon.bukkit.module.persistent.PersistentJsonTypeManager;
import org.moxqeon.bukkit.module.scoreboard.ScoreboardManager;
import org.moxqeon.platform.Platform;
import org.moxqeon.platform.PlatformPluginAccessor;

public final class MoxBukkit extends JavaPlugin implements PlatformPluginAccessor<MoxLib> {
    public static final String NAMESPACE = "moxlib";
    private static MoxBukkit instance;
    private MoxLib api;
    private OnGroundFix onGroundFix;
    private ScoreboardManager scoreboardManager;
    private PersistentJsonTypeManager persistentJsonTypeManager;

    @NotNull
    public static MoxBukkit instance() {
        return Preconditions.checkNotNull(instance);
    }
    @Override
    public void onEnable() {
        Preconditions.checkState(instance == null, "Plugin shouldn't be enabled multiple times");
        try {
            Class.forName("io.papermc.paper.util.TraceUtil");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Please use Paper (or its fork) to load MoxLib");
        }
        instance = this;
        api = new MoxLib(Platform.BUKKIT);
        onGroundFix = new OnGroundFix(this);
        scoreboardManager = new ScoreboardManager(this);
        persistentJsonTypeManager = new PersistentJsonTypeManager();
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemPickUpListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DamageManager(), this);

    }

    public void onDisable() {
        Preconditions.checkState((instance != null), "Plugin is not enabled");
        instance = null;
    }

    @NotNull
    public OnGroundFix getOnGroundFix() {
        return onGroundFix;
    }

    @NotNull
    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    @NotNull
    public PersistentJsonTypeManager getPersistentJsonTypeManager() {
        return persistentJsonTypeManager;
    }

    @NotNull
    @Override
    public MoxLib getAccess() {
        return api;
    }
}
