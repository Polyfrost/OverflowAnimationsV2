package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.polyfrost.overflowanimations.config.ItemPositionAdvancedSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.TransformTypeHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RenderFish.class, priority = 2000)
public class RenderFishMixin {
    @Redirect(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", at = @At(value = "NEW", target = "(DDD)Lnet/minecraft/util/Vec3;", ordinal = 0))
    private Vec3 oldFishingLine(double x, double y, double z) {
        ItemPositionAdvancedSettings advanced = OldAnimationsSettings.advancedSettings;
        double fov = Minecraft.getMinecraft().gameSettings.fovSetting;
        double decimalFov = fov / 110;
        return OldAnimationsSettings.fishingRodPosition && OldAnimationsSettings.INSTANCE.enabled ?
                ItemPositionAdvancedSettings.customRodLine ?
                        new Vec3(advanced.fishingLinePositionX, advanced.fishingLinePositionY, advanced.fishingLinePositionZ) :
                OldAnimationsSettings.fixRod ?
                        new Vec3(((-decimalFov + (decimalFov / 2.5)) - (decimalFov / 8)) + 0.16, 0, 0.4D) :
                new Vec3(-0.5D, 0.03D, 0.8D) : new Vec3(x, y, z);
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

}
