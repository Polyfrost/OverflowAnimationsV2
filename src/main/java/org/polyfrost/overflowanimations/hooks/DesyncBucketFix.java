package org.polyfrost.overflowanimations.hooks;

import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.events.event.SendPacketEvent;
import cc.polyfrost.oneconfig.events.event.Stage;
import cc.polyfrost.oneconfig.events.event.TickEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import cc.polyfrost.oneconfig.utils.TickDelay;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.mixin.C03PacketPlayerAccessor;

public class DesyncBucketFix {
    private final Minecraft mc = Minecraft.getMinecraft();
    private BucketPacketData packetData;
    private C08PacketPlayerBlockPlacement packetToSend;

    public void init() {
        EventManager.INSTANCE.register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent event) {
        if (!OldAnimationsSettings.bucketFix) return;
        if (!isItemBucket(event.entityPlayer.inventory.getCurrentItem())) return;
        if (packetData == null) return;
        event.setCanceled(true);
    }

    @Subscribe
    public void onPacketSent(SendPacketEvent event) {
        if (!OldAnimationsSettings.bucketFix) return;
        if (event.packet instanceof C08PacketPlayerBlockPlacement) {
            captureBucketPacket((C08PacketPlayerBlockPlacement) event.packet, event);
        } else if (event.packet instanceof C03PacketPlayer) {
            modifyPacketRotation((C03PacketPlayer) event.packet);
        }
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (!OldAnimationsSettings.bucketFix) return;
        if (event.stage != Stage.END) return;
        if (packetToSend != null) {
            mc.getNetHandler().addToSendQueue(packetToSend);
        }
    }

    private void captureBucketPacket(C08PacketPlayerBlockPlacement packet, SendPacketEvent event) {
        if (!isItemBucket(packet.getStack())) return;
        if (packetData == null) {
            packetData = new BucketPacketData(packet, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
            event.isCancelled = true;
        } else if (packetData.packet == packet) {
            packetData = null;
        }
    }

    private void modifyPacketRotation(C03PacketPlayer packet) {
        if (packetData == null) return;
        if (!packet.getRotating()) return;
        C03PacketPlayerAccessor packetAccessor = (C03PacketPlayerAccessor) packet;
        packetAccessor.setYaw(packetData.yaw);
        packetAccessor.setPitch(packetData.pitch);
    }

    private static boolean isItemBucket(ItemStack item) {
        return item != null && item.getItem() instanceof ItemBucket;
    }

    private static class BucketPacketData {
        public final C08PacketPlayerBlockPlacement packet;
        public final float yaw;
        public final float pitch;

        public BucketPacketData(C08PacketPlayerBlockPlacement packet, float yaw, float pitch) {
            this.packet = packet;
            this.yaw = yaw;
            this.pitch = pitch;
        }
    }
}
