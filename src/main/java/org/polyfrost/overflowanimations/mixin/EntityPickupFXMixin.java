package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.particle.EntityPickupFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPickupFX.class)
public class EntityPickupFXMixin {

    @Inject(method = "renderParticle", at = @At("HEAD"), cancellable = true)
    private void disableCollectParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        if (OldAnimationsSettings.disablePickup && OldAnimationsSettings.INSTANCE.enabled) {
            ci.cancel();
        }
    }

}
