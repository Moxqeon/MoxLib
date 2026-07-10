package org.moxqeon.bungee;

import com.google.common.base.Preconditions;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.moxqeon.MoxLib;
import org.moxqeon.bungee.module.bridge.BridgeManager;
import org.moxqeon.platform.Platform;
import org.moxqeon.platform.PlatformPluginAccessor;

import java.util.Map;

public final class MoxBungee extends Plugin implements PlatformPluginAccessor<MoxLib> {
    private static MoxBungee instance;
    private MoxLib api;
    private Map<String, Object> config;
    private BridgeManager bridgeManager;

    public static MoxBungee instance() {
        return Preconditions.checkNotNull(instance);
    }

    public void onEnable() {
        Preconditions.checkState((instance == null), "Plugin shouldn't be enabled multiple times");
        instance = this;
        api = new MoxLib(Platform.BUNGEE);
        bridgeManager = new BridgeManager();
    }

    public void onDisable() {
        instance = null;
    }

    @NotNull
    @Override
    public MoxLib getAccess() {
        return api;
    }

    @NotNull
    public BridgeManager getBridgeManager() {
        return bridgeManager;
    }
}
