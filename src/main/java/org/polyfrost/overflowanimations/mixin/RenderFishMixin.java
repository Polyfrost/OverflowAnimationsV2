package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import org.polyfrost.overflowanimations.config.ItemPositionAdvancedSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.PatcherConfigHook;
import org.polyfrost.overflowanimations.hooks.SmoothSneakHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderFish.class, priority = 2000)
public class RenderFishMixin {

    @ModifyVariable(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", at = @At(value = "STORE", ordinal = 0), index = 23)
    private Vec3 overflowAnimations$modifyLinePosition(Vec3 vec3) {
        if (!OldAnimationsSettings.INSTANCE.enabled) { return vec3; }
        ItemPositionAdvancedSettings advanced = OldAnimationsSettings.advancedSettings;
        double fov = Minecraft.getMinecraft().gameSettings.fovSetting;
        double decimalFov = fov / 110;
        boolean isParallaxOffset = PatcherConfigHook.isParallaxFixEnabled();
        double xCoord = vec3.xCoord;
        double yCoord = vec3.yCoord;
        double zCoord = vec3.zCoord;
        if (OldAnimationsSettings.fishingRodPosition && !OldAnimationsSettings.fixRod) {
            xCoord = -0.5D + (isParallaxOffset ? -0.1D : 0.0D);
            yCoord = 0.03D;
            zCoord = 0.8D;
        } else if (OldAnimationsSettings.fixRod) {
            xCoord = (-decimalFov + (decimalFov / 2.5) - (decimalFov / 8)) + 0.16 + (isParallaxOffset ? 0.15D : 0.0D);
            yCoord = 0.0D;
            zCoord = 0.4D;
        }
        if (ItemPositionAdvancedSettings.customRodLine) {
            xCoord = advanced.fishingLinePositionX;
            yCoord = advanced.fishingLinePositionY;
            zCoord = advanced.fishingLinePositionZ;
        }
        return new Vec3(xCoord, yCoord, zCoord);
    }

    @ModifyConstant(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V",
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/util/MathHelper;cos(F)F"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;isSneaking()Z")
            ),
            constant = @Constant(doubleValue = 0.8D)
    )
    public double overflowAnimations$moveLinePosition(double constant) {
        return overflowAnimations$moveVecLine(constant);
    }

    @Redirect(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getEyeHeight()F"))
    public float overflowAnimations$modifyEyeHeight(EntityPlayer instance) {
        return SmoothSneakHook.getSmoothSneak();
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;begin(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V", ordinal = 1))
    private void overflowAnimations$modifyLineThickness(EntityFishHook entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (!OldAnimationsSettings.INSTANCE.enabled) { return; }
        GL11.glLineWidth(1.0f + OldAnimationsSettings.INSTANCE.rodThickness);
    }

    @Unique
    private double overflowAnimations$moveVecLine(double constant) {
        return OldAnimationsSettings.fishingStick && OldAnimationsSettings.INSTANCE.enabled ? 0.85D : constant;
    }

}
