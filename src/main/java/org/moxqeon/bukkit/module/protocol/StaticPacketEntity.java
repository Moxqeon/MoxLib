package org.moxqeon.bukkit.module.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.moxqeon.util.MathUtil;

import java.util.UUID;

public abstract class StaticPacketEntity {
    protected final Entity handle;
    protected final PacketContainer spawnPacket;
    protected final PacketContainer metadataPacket;
    protected Location location;
    protected ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    protected <T extends Entity> StaticPacketEntity(@NotNull Class<T> entityClass, @NotNull Location location, @Nullable Consumer<T> consumer) {
        handle = location.getWorld().spawn(location, entityClass, consumer);
        handle.remove();
        this.location = handle.getLocation();
        spawnPacket = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
        metadataPacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
    }

    public int id() {
        return handle.getEntityId();
    }

    public UUID uuid() {
        return handle.getUniqueId();
    }

    public PacketContainer generateSpawnPacket() {
        spawnPacket.getIntegers()
                .write(0, handle.getEntityId())
                .write(4, MathUtil.floor(location.getPitch() / 360 * 256))
                .write(5, MathUtil.floor(location.getYaw() / 360 * 256));
        spawnPacket.getUUIDs().write(0, handle.getUniqueId());
        spawnPacket.getEntityTypeModifier().write(0, handle.getType());
        spawnPacket.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());
        return spawnPacket;
    }

    public PacketContainer generateMetaDataPacket() {
        metadataPacket.getIntegers().write(0, handle.getEntityId());
        metadataPacket.getWatchableCollectionModifier().write(0, WrappedDataWatcher.getEntityWatcher(handle).getWatchableObjects());
        return metadataPacket;
    }
//
//    public void spawnAtLocation(@NotNull Location location) {
//        this.location = location;
//        List<Player> audiences = location.getWorld().getPlayers();
//        Bukkit.broadcastMessage(audiences.toString());
//        PacketContainer destroyPacket = PacketUtil.generateDestroyPacket(handle.getEntityId());
//        protocolManager.broadcastServerPacket(destroyPacket, audiences);
//        Bukkit.getScheduler().runTaskLater(MoxBukkit.getInstance(), () -> {
//            protocolManager.broadcastServerPacket(generateSpawnPacket(), audiences);
//            protocolManager.broadcastServerPacket(generateMetaDataPacket(), audiences);
//        }, 1);
//    }


}
