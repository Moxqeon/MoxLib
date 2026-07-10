package org.moxqeon.bukkit.fix;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.moxqeon.bukkit.MoxBukkit;
import org.moxqeon.bukkit.event.PlayerLandEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class OnGroundFix {
    private final Map<UUID, Boolean> onGroundMap = new ConcurrentHashMap<>();

    private final List<PlayerLandEvent> eventQueue = new ArrayList<>();

    public OnGroundFix(@NotNull MoxBukkit moxBukkit) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(moxBukkit, ListenerPriority.HIGHEST, PacketType.Play.Client.POSITION) {
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                UUID uuid = player.getUniqueId();
                PacketContainer packet = event.getPacket();
                boolean isOnGround = (packet.getBooleans().read(0));
                if (!onGroundMap.getOrDefault(uuid, true) && isOnGround) {
                    Location from = player.getLocation();
                    Location to = new Location(player.getWorld(), packet.getDoubles().read(0), packet.getDoubles().read(1), packet.getDoubles().read(2), from.getYaw(), from.getPitch());
                    PlayerLandEvent playerLandEvent = new PlayerLandEvent(player, from, to);
                    eventQueue.add(playerLandEvent);
                }
                onGroundMap.put(uuid, isOnGround);
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(moxBukkit, ListenerPriority.HIGHEST, PacketType.Play.Client.POSITION_LOOK) {
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                UUID uuid = player.getUniqueId();
                PacketContainer packet = event.getPacket();
                boolean isOnGround = packet.getBooleans().read(0);
                if (!onGroundMap.getOrDefault(uuid, true) && isOnGround) {
                    Location from = player.getLocation();
                    float yaw = Location.normalizeYaw(packet.getFloat().read(0));
                    Location to = new Location(player.getWorld(), packet.getDoubles().read(0), packet.getDoubles().read(1), packet.getDoubles().read(2), yaw, packet.getFloat().read(1));
                    PlayerLandEvent playerLandEvent = new PlayerLandEvent(player, from, to);
                    eventQueue.add(playerLandEvent);
                }
                onGroundMap.put(uuid, isOnGround);
            }
        });
        Bukkit.getScheduler().runTaskTimer(moxBukkit, () -> {
            if (eventQueue.isEmpty()) return;
            for (PlayerLandEvent event : eventQueue)
                Bukkit.getPluginManager().callEvent(event);
            eventQueue.clear();
        }, 0, 1);
    }

    public boolean isOnGround(@NotNull Player player) {
        return onGroundMap.get(player.getUniqueId());
    }
}
