package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import org.polyfrost.overflowanimations.config.ItemPositionSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderEntityItem.class)
public class RenderEntityItemMixin_CustomPositions {

    //todo: not even god could save this code

    @Inject(method = "func_177077_a", at = @At("TAIL"))
    public void overflowAnimations$droppedItemTransforms(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_, CallbackInfoReturnable<Integer> cir) {
        OldAnimationsSettings settings = MainModSettings.INSTANCE.getOldSettings();
        ItemPositionSettings advanced = MainModSettings.INSTANCE.getPositionSettings();
        if (MainModSettings.INSTANCE.getOldSettings().getGlobalPositions() && settings.enabled) {
            GlStateManager.translate(
                    advanced.getDroppedPositionX(),
                    advanced.getDroppedPositionY(),
                    advanced.getDroppedPositionZ()
            );
            GlStateManager.rotate(advanced.getDroppedRotationPitch(), 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(advanced.getDroppedRotationYaw(), 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(advanced.getDroppedRotationRoll(), 0.0f, 0.0f, 1.0f);
            GlStateManager.scale(
                    1.0f * Math.exp(advanced.getDroppedScale()),
                    1.0f * Math.exp(advanced.getDroppedScale()),
                    1.0f * Math.exp(advanced.getDroppedScale())
            );
        }
    }

}
