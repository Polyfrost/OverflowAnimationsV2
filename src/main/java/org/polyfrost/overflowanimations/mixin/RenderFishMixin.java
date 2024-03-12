package org.polyfrost.overflowanimations.mixin;

import club.sk1er.patcher.config.PatcherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.ItemPositionAdvancedSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.TransformTypeHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderFish.class, priority = 2000)
public class RenderFishMixin {
    @Redirect(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", at = @At(value = "NEW", target = "(DDD)Lnet/minecraft/util/Vec3;", ordinal = 0))
    private Vec3 oldFishingLine(double x, double y, double z) {
        ItemPositionAdvancedSettings advanced = OldAnimationsSettings.advancedSettings;
        double fov = Minecraft.getMinecraft().gameSettings.fovSetting;
        double decimalFov = fov / 110;
        boolean isParallaxOffset = OverflowAnimations.isPatcherPresent && PatcherConfig.parallaxFix;
        if (OldAnimationsSettings.INSTANCE.enabled) {
            Vec3 fishingRod = new Vec3(x, y, z);
            if (OldAnimationsSettings.fishingRodPosition && !OldAnimationsSettings.fixRod) {
                fishingRod = new Vec3(-0.5D + (isParallaxOffset ? -0.1D : 0.0D), 0.03D, 0.8D);
            } else if (OldAnimationsSettings.fixRod) {
                fishingRod = new Vec3(((-decimalFov + (decimalFov / 2.5)) - (decimalFov / 8)) + 0.16 + (isParallaxOffset ? 0.15D : 0.0D), 0, 0.4D);
            }
            if (ItemPositionAdvancedSettings.customRodLine) {
                fishingRod = new Vec3(advanced.fishingLinePositionX, advanced.fishingLinePositionY, advanced.fishingLinePositionZ);
            }
            return fishingRod;
        }
        return new Vec3(x, y, z);

    }

    @ModifyConstant(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", constant = @Constant(doubleValue = 0.8D, ordinal = 1))
    public double moveLinePosition(double constant) {
        return OldAnimationsSettings.fishingStick && OldAnimationsSettings.INSTANCE.enabled ? 0.85D : constant;
    }

    @ModifyConstant(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", constant = @Constant(doubleValue = 0.8D, ordinal = 2))
    public double moveLinePosition2(double constant) {
        return OldAnimationsSettings.fishingStick && OldAnimationsSettings.INSTANCE.enabled ? 0.85D : constant;
    }

    @Redirect(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getEyeHeight()F"))
    public float modifyEyeHeight(EntityPlayer instance) {
        return OldAnimationsSettings.smoothSneaking && OldAnimationsSettings.INSTANCE.enabled ? TransformTypeHook.sneakingHeight : instance.getEyeHeight();
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;begin(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V", ordinal = 1))
    private void modifyLineThickness(EntityFishHook entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        GL11.glLineWidth(1.0f +
                (OldAnimationsSettings.rodThickBool && OldAnimationsSettings.INSTANCE.enabled ?
                    OldAnimationsSettings.INSTANCE.rodThickness : 0.0F));
    }

}
