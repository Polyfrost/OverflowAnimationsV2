package org.polyfrost.overflowanimations.hooks;

import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.events.event.SendPacketEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;

import java.util.ArrayList;
import java.util.List;

public class DesyncBucketFix {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final List<C08PacketPlayerBlockPlacement> packetQueue = new ArrayList<>();
    private static boolean capturing = true;

    public static void init() {
        EventManager.INSTANCE.register(new DesyncBucketFix());
    }

    @Subscribe
    public void onPacketSent(SendPacketEvent event) {
        if (!OldAnimationsSettings.INSTANCE.enabled) return;
        if (!OldAnimationsSettings.bucketFix) return;
        if (!capturing) return;
        if (event.packet instanceof C08PacketPlayerBlockPlacement) {
            captureBucketPacket((C08PacketPlayerBlockPlacement) event.packet, event);
        }
    }

    public static void afterPacketSent(Packet<?> packetSent) {
        if (!OldAnimationsSettings.INSTANCE.enabled) return;
        if (!OldAnimationsSettings.bucketFix) return;
        if (!capturing) return;
        if (!(packetSent instanceof C03PacketPlayer)) return;
        if (packetQueue.isEmpty()) return;

        capturing = false;
        for (C08PacketPlayerBlockPlacement packet : packetQueue) {
            mc.getNetHandler().addToSendQueue(packet);

        }
        capturing = true;
        packetQueue.clear();
    }

    private static void captureBucketPacket(C08PacketPlayerBlockPlacement packet, SendPacketEvent event) {
        if (!isItemBucket(packet.getStack())) return;
        event.isCancelled = true;
        packetQueue.add(packet);
    }

    private static boolean isItemBucket(ItemStack item) {
        return item != null && item.getItem() instanceof ItemBucket;
    }
}
