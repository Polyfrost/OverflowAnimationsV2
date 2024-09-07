package org.polyfrost.overflowanimations.mixin.layers;

import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.passive.EntitySheep;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerSheepWool.class)
public abstract class LayerSheepWoolMixin {

    @Shadow @Final private ModelSheep1 sheepModel;

    @Shadow @Final private RenderSheep sheepRenderer;

    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/passive/EntitySheep;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelSheep1;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    public void overflowAnimations$renderHitColor(EntitySheep entitylivingbaseIn, float f, float g, float partialTicks, float h, float i, float j, float scale, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.armorDamageTintStyle == 1 && OldAnimationsSettings.INSTANCE.enabled) {
            boolean bl = entitylivingbaseIn.hurtTime > 0 || entitylivingbaseIn.deathTime > 0;
            HitColorHook.renderHitColorPre(entitylivingbaseIn, bl, partialTicks, sheepRenderer);
            if (bl) {
                sheepModel.render(entitylivingbaseIn, f, g, h, i, j, scale);
            }
            HitColorHook.renderHitColorPost(bl);
        }
    }

}
