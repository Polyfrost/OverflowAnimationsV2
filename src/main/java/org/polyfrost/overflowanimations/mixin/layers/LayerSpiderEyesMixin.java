package org.polyfrost.overflowanimations.mixin.layers;

import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.layers.LayerSpiderEyes;
import net.minecraft.entity.monster.EntitySpider;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerSpiderEyes.class)
public abstract class LayerSpiderEyesMixin {

    @Shadow @Final private RenderSpider<?> spiderRenderer;

    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/monster/EntitySpider;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    public void overflowAnimations$renderHitColor(EntitySpider entitylivingbaseIn, float f, float g, float partialTicks, float h, float i, float j, float scale, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.armorDamageTintStyle == 1 && OldAnimationsSettings.INSTANCE.enabled) {
            boolean bl = entitylivingbaseIn.hurtTime > 0 || entitylivingbaseIn.deathTime > 0;
            HitColorHook.renderHitColorPre(entitylivingbaseIn, bl, partialTicks, spiderRenderer);
            if (bl) {
                spiderRenderer.getMainModel().render(entitylivingbaseIn, f, g, h, i, j, scale);
            }
            HitColorHook.renderHitColorPost(bl);
        }
    }

}
