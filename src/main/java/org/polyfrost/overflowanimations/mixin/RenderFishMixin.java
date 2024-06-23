package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import org.polyfrost.overflowanimations.config.ItemPositionSettings;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.PatcherConfigHook;
import org.polyfrost.overflowanimations.hooks.SneakHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderFish.class, priority = 2000)
public class RenderFishMixin {

    //todo: holy shit. rewrite

    //todo: modifyargs possibly
    @Redirect(
            method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V",
            at = @At(
                    value = "NEW",
                    target = "(DDD)Lnet/minecraft/util/Vec3;",
                    ordinal = 0
            )
    )
    private Vec3 overflowAnimations$oldFishingLine(double x, double y, double z) {
        ItemPositionSettings advanced = MainModSettings.INSTANCE.getPositionSettings();
        double fov = Minecraft.getMinecraft().gameSettings.fovSetting;
        double decimalFov = fov / 110;
        boolean isParallaxOffset = PatcherConfigHook.isParallaxFixEnabled();
        if (MainModSettings.INSTANCE.getOldSettings().enabled) {
            Vec3 fishingRod = new Vec3(x, y, z);
            if (MainModSettings.INSTANCE.getOldSettings().getFishingRodPosition() && !MainModSettings.INSTANCE.getOldSettings().getFixRod()) {
                fishingRod = new Vec3(-0.5D + (isParallaxOffset ? -0.1D : 0.0D), 0.03D, 0.8D);
            } else if (MainModSettings.INSTANCE.getOldSettings().getFixRod()) {
                fishingRod = new Vec3(((-decimalFov + (decimalFov / 2.5)) - (decimalFov / 8)) + 0.16 + (isParallaxOffset ? 0.15D : 0.0D), 0, 0.4D);
            }
            if (advanced.getCustomRodLine()) {
                fishingRod = new Vec3(advanced.getFishingLinePositionX(), advanced.getFishingLinePositionY(), advanced.getFishingLinePositionZ());
            }
            return fishingRod;
        }
        return new Vec3(x, y, z);

    }

    @ModifyConstant(
            method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V",
            constant = @Constant(
                    doubleValue = 0.8D,
                    ordinal = 1
            )
    )
    public double overflowAnimations$moveLinePosition(double constant) {
        return overflowAnimations$rodLineTranslation(constant, 0.85D);
    }

    @ModifyConstant(
            method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V",
            constant = @Constant(
                    doubleValue = 0.8D,
                    ordinal = 2
            )
    )
    public double overflowAnimations$moveLinePosition2(double constant) {
        return overflowAnimations$rodLineTranslation(constant, 0.85D);
    }

    @Redirect(
            method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;getEyeHeight()F"
            )
    )
    public float overflowAnimations$modifyEyeHeight(EntityPlayer instance) {
        return SneakHook.INSTANCE.getSmoothSneak();
    }

    @Inject(
            method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/WorldRenderer;begin(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V",
                    ordinal = 1
            )
    )
    private void overflowAnimations$modifyLineThickness(EntityFishHook entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        OldAnimationsSettings settings = MainModSettings.INSTANCE.getOldSettings();
        if (!settings.enabled) { return; }
        GL11.glLineWidth(1.0f + (settings.getRodThickBool() ? settings.getRodThickness() : 0.0F));
    }

    //todo: use slice instead, silly
    @Unique
    private double overflowAnimations$rodLineTranslation(double original, double value) {
        if (MainModSettings.INSTANCE.getOldSettings().getFishingStick() && MainModSettings.INSTANCE.getOldSettings().enabled) {
            return 0.85;
        }
        return original;
    }

}
