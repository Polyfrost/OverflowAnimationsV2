package org.polyfrost.overflowanimations.mixin;

import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.DebugOverlayHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowanimations.hooks.TransformTypeHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Unique
    @Final
    private final Minecraft overflowAnimations$mc = Minecraft.getMinecraft();
    @Unique
    private float overflow$height;
    @Unique
    private float overflow$previousHeight;

    @Inject(method = "setupCameraTransform", at = @At("HEAD"))
    protected void simplified$setupCameraTransform(float partialTicks, int pass, CallbackInfo ci) {
        TransformTypeHook.sneakingHeight = overflow$previousHeight + (overflow$height - overflow$previousHeight) * partialTicks;
    }

    @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    public float modifyEyeHeight(Entity entity, float partialTicks) {
        return OldAnimationsSettings.smoothSneaking && OldAnimationsSettings.INSTANCE.enabled ? TransformTypeHook.sneakingHeight : entity.getEyeHeight();
    }

    @Redirect(method = "renderWorldDirections", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    public float syncCrossHair(Entity entity, float partialTicks) {
        return modifyEyeHeight(entity, partialTicks);
    }

    @Inject(method = "renderWorldDirections", at = {@At("HEAD")}, cancellable = true)
    public void renderCrosshair(float partialTicks, CallbackInfo ci) {
        if ((OldAnimationsSettings.INSTANCE.debugCrosshairMode != 1) && OldAnimationsSettings.INSTANCE.enabled)
            ci.cancel();
    }

    @Inject(method = "updateRenderer", at = @At("HEAD"))
    private void interpolateHeight(CallbackInfo ci) {
        Entity entity = overflowAnimations$mc.getRenderViewEntity();
        float eyeHeight = entity.getEyeHeight();
        overflow$previousHeight = overflow$height;
        if (eyeHeight < overflow$height)
            overflow$height = eyeHeight;
        else if (OldAnimationsSettings.longerUnsneak)
            overflow$height += (eyeHeight - overflow$height) * 0.5f;
        DebugOverlayHook.setOverflowEyeHeight(overflow$height);
    }

    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    public void cancelHurtCamera(float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.noHurtCam && OldAnimationsSettings.INSTANCE.enabled)
            ci.cancel();
    }

    @Inject(method = "setupViewBobbing", at = {@At("HEAD")}, cancellable = true)
    public void smartBobbing(float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.modernBobbing && OldAnimationsSettings.INSTANCE.enabled && !overflowAnimations$mc.getRenderViewEntity().onGround)
            ci.cancel();
    }

}
