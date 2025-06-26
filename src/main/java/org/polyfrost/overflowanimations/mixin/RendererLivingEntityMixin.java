package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.SmoothSneakHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public abstract class RendererLivingEntityMixin<T extends EntityLivingBase> extends Render<T> {
    protected RendererLivingEntityMixin(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"))
    public void overflowAnimations$movePlayerModel(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.enabled &&
                OldAnimationsSettings.smoothModelSneak &&
                entity instanceof EntityPlayerSP &&
                entity.getName().equals(Minecraft.getMinecraft().thePlayer.getName())) {
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, -0.2F, 0.0F);
            }

            GlStateManager.translate(0.0F, 1.62F - SmoothSneakHook.getSmoothSneak(entity.getEyeHeight()), 0.0F);
        }
    }

    @Inject(method = "rotateCorpse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", shift = At.Shift.AFTER))
    public void overflowAnimations$rotateCorpse(T bat, float p_77043_2_, float p_77043_3_, float partialTicks, CallbackInfo ci) {
        final boolean player = bat.getName().equals(Minecraft.getMinecraft().thePlayer.getName());
        if (OldAnimationsSettings.INSTANCE.enabled) {
            if (OldAnimationsSettings.dinnerBoneMode && player) {
                overflowAnimations$dinnerboneRotation(bat);
            } else if (OldAnimationsSettings.dinnerBoneModeEntities && !player) {
                overflowAnimations$dinnerboneRotation(bat);
            }
        }
    }

    @Unique
    private static void overflowAnimations$dinnerboneRotation(EntityLivingBase entity) {
        GlStateManager.translate(0.0f, entity.height + 0.1f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
    }
}
