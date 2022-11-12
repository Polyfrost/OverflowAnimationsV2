package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import cc.polyfrost.overflowanimations.handlers.MicroSpriteHandler;
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

    @ModifyArg(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1), index = 0)
    private float fixRotation(float angle) {
        return (renderManager.options.thirdPersonView == 2 && OldAnimationsSettings.oldProjectiles && OverflowAnimations.oldAnimationsSettings.enabled ? -1F : 1F) * angle;
    }

    @Inject(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1, shift = At.Shift.AFTER))
    private void flipProjectile(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.oldProjectiles && OverflowAnimations.oldAnimationsSettings.enabled) {
            GlStateManager.translate(-0.07, 0.47, 0.1);
        }
    }

    @Redirect(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    public void changeToSprite(RenderItem instance, ItemStack item, ItemCameraTransforms.TransformType type) {
        if (OldAnimationsSettings.oldProjectiles && OldAnimationsSettings.projectileSprites && OverflowAnimations.oldAnimationsSettings.enabled) {
            MicroSpriteHandler.oldProjectileRender(instance, item);
        } else {
            instance.renderItem(item, type);
        }
    }

}
