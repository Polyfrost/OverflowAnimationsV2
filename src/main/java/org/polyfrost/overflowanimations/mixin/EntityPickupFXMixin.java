package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.particle.EntityPickupFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPickupFX.class)
public class EntityPickupFXMixin {

    @Shadow private float field_174841_aA;
    @Shadow private Entity field_174843_ax;

    @Inject(method = "renderParticle", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/particle/EntityPickupFX;field_174841_aA:F"))
    private void overflowAnimations$factorInEyeHeight(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        if (!OldAnimationsSettings.INSTANCE.enabled) { return; }
        if (OldAnimationsSettings.oldPickup) {
            field_174841_aA = (field_174843_ax.getEyeHeight() / 2);
        }
        field_174841_aA += OldAnimationsSettings.INSTANCE.pickupPosition;
    }

    @Inject(method = "renderParticle", at = @At("HEAD"), cancellable = true)
    private void overflowAnimations$disableCollectParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        if (OldAnimationsSettings.disablePickup && OldAnimationsSettings.INSTANCE.enabled) {
            ci.cancel();
        }
    }

}
