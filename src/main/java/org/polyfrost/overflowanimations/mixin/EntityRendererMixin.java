package org.polyfrost.overflowanimations.mixin;

import org.polyfrost.overflowanimations.config.MainModSettings;
import org.polyfrost.overflowanimations.hooks.DebugOverlayHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowanimations.hooks.SneakHook;
import org.polyfrost.overflowanimations.util.MathUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    //todo: use sneak hook

    @Shadow private Minecraft mc;
    @Unique private float overflow$height;
    @Unique private float overflow$previousHeight;

    @Inject(
            method = "setupCameraTransform",
            at = @At(
                    value = "HEAD"
            )
    )
    protected void overflowAnimations$getInterpolatedEyeHeight(float partialTicks, int pass, CallbackInfo ci) {
        if (!MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        SneakHook.INSTANCE.setSneakingHeight(MathUtils.INSTANCE.interpolate(overflow$previousHeight, overflow$height, partialTicks));
    }

    @ModifyVariable(
            method = "orientCamera",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            ),
            index = 3
    )
    public float overflowAnimations$modifyEyeHeight(float eyeHeight) {
        return SneakHook.INSTANCE.getSmoothSneak();
    }

    @ModifyArg(
            method = "renderWorldDirections",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"
            ),
            index = 1
    )
    public float overflowAnimations$syncCrossHair(float x) {
        return SneakHook.INSTANCE.getSmoothSneak();
    }

    @Inject(
            method = "renderWorldDirections",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    public void overflowAnimations$renderCrosshair(float partialTicks, CallbackInfo ci) {
        if (MainModSettings.INSTANCE.getOldSettings().getDebugCrosshairMode() == 1 || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        ci.cancel();
    }

    @Inject(
            method = "updateRenderer",
            at = @At(
                    value = "HEAD"
            )
    )
    private void overflowAnimations$interpolateHeight(CallbackInfo ci) {
        if (!MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        Entity entity = mc.getRenderViewEntity();
        float eyeHeight = entity.getEyeHeight();
        overflow$previousHeight = overflow$height;
        if (MainModSettings.INSTANCE.getOldSettings().getLongerUnsneak()) {
            if (eyeHeight < overflow$height)
                overflow$height = eyeHeight;
            else
                overflow$height += (eyeHeight - overflow$height) * 0.5f;
        } else {
            overflow$height = eyeHeight;
        }
        DebugOverlayHook.setOverflowEyeHeight(overflow$height);
    }

    @Inject(
            method = "hurtCameraEffect",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    public void overflowAnimations$cancelHurtCamera(float partialTicks, CallbackInfo ci) {
        if (!MainModSettings.INSTANCE.getOldSettings().getNoHurtCam() || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        ci.cancel();
    }

    @ModifyArg(
            method = "setupViewBobbing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V",
                    ordinal = 2
            ),
            index = 0
    )
    public float overflowAnimations$modernBobbing(float angle, float x, float y, float z) {
        if (!MainModSettings.INSTANCE.getOldSettings().getModernBobbing() || !MainModSettings.INSTANCE.getOldSettings().enabled) {
            return angle;
        }
        return 0.0F;
    }

}
