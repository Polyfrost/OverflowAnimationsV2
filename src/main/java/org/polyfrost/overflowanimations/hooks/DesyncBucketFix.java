package org.polyfrost.overflowanimations.hooks;

import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.events.event.SendPacketEvent;
import cc.polyfrost.oneconfig.events.event.Stage;
import cc.polyfrost.oneconfig.events.event.TickEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;

import java.util.ArrayList;
import java.util.List;

public class DesyncBucketFix {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final List<C08PacketPlayerBlockPlacement> packetQueue = new ArrayList<>();
    private boolean capturing;

    public void init() {
        EventManager.INSTANCE.register(this);
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

    @Subscribe
    public void onTick(TickEvent event) {
        if (!OldAnimationsSettings.INSTANCE.enabled) return;
        if (!OldAnimationsSettings.bucketFix) return;
        capturing = event.stage == Stage.START;
        if (capturing) return;
        if (packetQueue.isEmpty()) return;

        for (C08PacketPlayerBlockPlacement packet : packetQueue) {
            mc.getNetHandler().addToSendQueue(packet);
        }
        packetQueue.clear();
    }

    private void captureBucketPacket(C08PacketPlayerBlockPlacement packet, SendPacketEvent event) {
        if (!isItemBucket(packet.getStack())) return;
        event.isCancelled = true;
        packetQueue.add(packet);
    }

    private static boolean isItemBucket(ItemStack item) {
        return item != null && item.getItem() instanceof ItemBucket;
    }
}
