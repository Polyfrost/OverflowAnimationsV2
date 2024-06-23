package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.polyfrost.overflowanimations.hooks.SneakHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public abstract class RendererLivingEntityMixin<T extends EntityLivingBase> extends Render<T> {

    //todo: maintenance

    protected RendererLivingEntityMixin(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(
            method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"
            )
    )
    public void overflowAnimations$movePlayerModel(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (!MainModSettings.INSTANCE.getOldSettings().getSmoothModelSneak() || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        if (entity instanceof AbstractClientPlayer) {
            boolean player = entity.getName().equals(Minecraft.getMinecraft().thePlayer.getName());
            float eyeHeight = player ? SneakHook.INSTANCE.getSneakingHeight() : entity.getEyeHeight();
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, -0.2F, 0.0F);
            }
            GlStateManager.translate(0.0F, 1.62F - eyeHeight, 0.0F);
        }
    }

    @Inject(
            method = "rotateCorpse",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V",
                    shift = At.Shift.AFTER
            )
    )
    public void overflowAnimations$rotateCorpse(T bat, float p_77043_2_, float p_77043_3_, float partialTicks, CallbackInfo ci) {
        boolean player = bat.getName().equals(Minecraft.getMinecraft().thePlayer.getName());
        if (MainModSettings.INSTANCE.getOldSettings().enabled) {
            if (MainModSettings.INSTANCE.getOldSettings().getDinnerBoneMode() && player) {
                overflowAnimations$dinnerboneRotation(bat);
            } else if (MainModSettings.INSTANCE.getOldSettings().getDinnerBoneModeEntities() && !player) {
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
