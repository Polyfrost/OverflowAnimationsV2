package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderEntityItem.class)
public abstract class RenderEntityItemMixin extends Render<EntityItem> {

    //todo: 2d items rotation broken

    @Unique private static final ThreadLocal<Boolean> overflowAnimations$isGui3d = ThreadLocal.withInitial(() -> false);

    protected RenderEntityItemMixin(RenderManager renderManager) {
        super(renderManager);
    }

    @ModifyVariable(
            method = "func_177077_a",
            at = @At(
                    value = "STORE"
            ),
            ordinal = 0
    )
    private boolean overflowAnimations$hookGui3d(boolean original) {
        overflowAnimations$isGui3d.set(original);
        return original;
    }

    @ModifyArg(
            method = "func_177077_a",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V"
            ),
            index = 0
    )
    private float overflowAnimations$apply2dItem(float angle) {
        if (MainModSettings.INSTANCE.getOldSettings().getItemSprites() && MainModSettings.INSTANCE.getOldSettings().enabled) { return angle; }
        if (!overflowAnimations$isGui3d.get()) {
            return 180.0F - renderManager.playerViewY;
        }
        return angle;
    }

    @Inject(
            method = "func_177077_a",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V",
                    shift = At.Shift.AFTER
            )
    )
    private void overflowAnimations$fix2dRotation(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_, CallbackInfoReturnable<Integer> cir) {
        if (!MainModSettings.INSTANCE.getOldSettings().getItemSprites() || !MainModSettings.INSTANCE.getOldSettings().enabled || !MainModSettings.INSTANCE.getOldSettings().getRotationFix()) { return; }
        if (overflowAnimations$isGui3d.get()) { return; }
        GlStateManager.rotate((Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? 1 : -1) * renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
    }

}
