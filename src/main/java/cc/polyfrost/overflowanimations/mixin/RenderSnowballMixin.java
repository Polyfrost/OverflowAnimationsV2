package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSnowball.class)
public abstract class RenderSnowballMixin<T extends Entity> extends Render<T> {
    public RenderSnowballMixin(RenderManager renderManager) {
        super(renderManager);
    }

    @ModifyArg(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 0), index = 0)
    private float fixRotationY(float original) {
        return OldAnimationsSettings.oldProjectiles && OverflowAnimations.oldAnimationsSettings.enabled ?
                180.0f - Minecraft.getMinecraft().getRenderManager().playerViewY : original;
    }

    @ModifyArg(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1), index = 0)
    private float fixRotationX(float original) {
        return (OldAnimationsSettings.oldProjectiles && OverflowAnimations.oldAnimationsSettings.enabled ? -1F : 1F) * original;
    }

    @Inject(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1, shift = At.Shift.AFTER))
    private void flipProjectile(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.itemTransformations  && OverflowAnimations.oldAnimationsSettings.enabled) {
            if (Minecraft.getMinecraft().getRenderManager().options.thirdPersonView != 2) {
                GlStateManager.scale(0.8f, 0.8f, 0.8f);
                GlStateManager.translate(-0.04, 0.40, 0.2);
            } else
                GlStateManager.translate(-0.09, 0.51, 0.0);
        }
    }

    @Redirect(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    public void changeToSprite(RenderItem instance, ItemStack item, ItemCameraTransforms.TransformType type) {
        if (OldAnimationsSettings.items2D && OldAnimationsSettings.projectileSprites && OverflowAnimations.oldAnimationsSettings.enabled) {
            GlStateManager.disableLighting();
            GlStateManager.scale(1.0F, 1.0F, 0.0F);
            instance.renderItem(item, ItemCameraTransforms.TransformType.GROUND);
            GlStateManager.enableLighting();
        } else {
            instance.renderItem(item, type);
        }
    }
}
