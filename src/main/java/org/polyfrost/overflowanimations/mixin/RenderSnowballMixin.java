package org.polyfrost.overflowanimations.mixin;

import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSnowball.class)
public abstract class RenderSnowballMixin<T extends Entity> extends Render<T> {
    public RenderSnowballMixin(RenderManager renderManager) {
        super(renderManager);
    }

    @ModifyArg(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 0), index = 0)
    private float fixRotationY(float original) {
        return OldAnimationsSettings.oldProjectiles && OldAnimationsSettings.INSTANCE.enabled ?
                180.0f - Minecraft.getMinecraft().getRenderManager().playerViewY : original;
    }

    @ModifyArg(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1), index = 0)
    private float fixRotationX(float original) {
        return (OldAnimationsSettings.oldProjectiles && OldAnimationsSettings.INSTANCE.enabled ? -1F : 1F) * original;
    }

    @Inject(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1, shift = At.Shift.AFTER))
    private void flipProjectile(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.itemTransformations && OldAnimationsSettings.INSTANCE.enabled) {
            if (Minecraft.getMinecraft().getRenderManager().options.thirdPersonView != 2) {
                GlStateManager.scale(0.8f, 0.8f, 0.8f);
                GlStateManager.translate(-0.04, 0.40, 0.2);
            } else
                GlStateManager.translate(-0.09, 0.51, 0.0);
        }
    }

    @Inject(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    public void changeToSprite(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.items2D && OldAnimationsSettings.projectileSprites && OldAnimationsSettings.INSTANCE.enabled) {
            GlStateManager.disableLighting();
            GlStateManager.scale(1.0F, 1.0F, 0.0F);
        }
    }

    @ModifyArg(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"), index = 1)
    private ItemCameraTransforms.TransformType modifyItemTransformation(ItemCameraTransforms.TransformType cameraTransformType) {
        if (OldAnimationsSettings.items2D && OldAnimationsSettings.projectileSprites && OldAnimationsSettings.INSTANCE.enabled) {
            return ItemCameraTransforms.TransformType.GROUND;
        }
        return cameraTransformType;
    }

    @Inject(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V", shift = At.Shift.AFTER))
    public void changeToSprite2(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.items2D && OldAnimationsSettings.projectileSprites && OldAnimationsSettings.INSTANCE.enabled) {
            GlStateManager.enableLighting();
        }
    }
}
