package org.moxqeon.bukkit.module.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.moxqeon.bukkit.MoxBukkit;

import java.util.logging.Level;

public class Tester {
    public static void test() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(MoxBukkit.instance(), PacketType.Play.Server.BLOCK_BREAK_ANIMATION) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                MoxBukkit.instance().getLogger().log(Level.INFO, "Block Break 0: " + packet.getIntegers().read(0));
                MoxBukkit.instance().getLogger().log(Level.INFO, "Block Break 1: " + packet.getIntegers().read(1));

            }
        });
    }
}
