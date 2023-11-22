package org.polyfrost.overflowanimations.mixin;

import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RenderFish.class, priority = 2000)
public class RenderFishMixin {
    @Redirect(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", at = @At(value = "NEW", target = "(DDD)Lnet/minecraft/util/Vec3;", ordinal = 0))
    private Vec3 oldFishingLine(double x, double y, double z) {
        double fov = Minecraft.getMinecraft().gameSettings.fovSetting;
        double decimalFov = fov / 110;
        return OldAnimationsSettings.itemTransformations && OldAnimationsSettings.INSTANCE.enabled ? OldAnimationsSettings.fixRod ?
                new Vec3(((-decimalFov + (decimalFov / 2.5)) - (decimalFov / 8)) + 0.16, 0, 0.4D) :
                new Vec3(-0.5D, 0.03D, 0.8D) : new Vec3(x, y, z);
    }

    @ModifyConstant(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", constant = @Constant(doubleValue = 0.0D))
    private double lowerLine(double original) {
        return OldAnimationsSettings.thirdTransformations && OldAnimationsSettings.INSTANCE.enabled ?
                -0.1875D : original;
    }
}
