package org.polyfrost.overflowanimations.mixin.layers;

import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.client.renderer.entity.layers.LayerWolfCollar;
import net.minecraft.entity.passive.EntityWolf;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerWolfCollar.class)
public abstract class LayerWolfCollarMixin {

    @Shadow @Final private RenderWolf wolfRenderer;

    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/passive/EntityWolf;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    public void overflowAnimations$renderHitColor(EntityWolf entitylivingbaseIn, float f, float g, float partialTicks, float h, float i, float j, float scale, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.armorDamageTintStyle == 1 && OldAnimationsSettings.INSTANCE.enabled) {
            boolean bl = entitylivingbaseIn.hurtTime > 0 || entitylivingbaseIn.deathTime > 0;
            HitColorHook.renderHitColorPre(entitylivingbaseIn, bl, partialTicks, wolfRenderer);
            if (bl) {
                wolfRenderer.getMainModel().render(entitylivingbaseIn, f, g, h, i, j, scale);
            }
            HitColorHook.renderHitColorPost(bl);
        }
    }

}
