package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.DesyncBucketFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientMixin {

    @ModifyConstant(method = "handleSpawnExperienceOrb", constant = @Constant(doubleValue = 32.0D))
    private double oldXPOrbs(double original) {
        return OldAnimationsSettings.oldXPOrbs && OldAnimationsSettings.INSTANCE.enabled ? 1.0d : original;
    }

    @ModifyConstant(method = "handleCollectItem", constant = @Constant(floatValue = 0.5f))
    private float oldItemPickup(float original) {
        return OldAnimationsSettings.oldPickup && OldAnimationsSettings.INSTANCE.enabled ? 0.9f : original;
    }

    @Inject(method = "addToSendQueue", at = @At("TAIL"))
    private void afterPacketSent(Packet<?> packetIn, CallbackInfo ci) {
        DesyncBucketFix.afterPacketSent(packetIn);
    }
}