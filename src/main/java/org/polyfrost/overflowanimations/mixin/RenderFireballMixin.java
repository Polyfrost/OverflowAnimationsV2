package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.config.ItemPositionAdvancedSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderFireball.class)
public abstract class RenderFireballMixin extends Render<EntityFireball> {

    protected RenderFireballMixin(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/projectile/EntityFireball;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V", shift = At.Shift.AFTER), cancellable = true)
    public void overflowAnimations$changeToModel(EntityFireball entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        OldAnimationsSettings settings = OldAnimationsSettings.INSTANCE;
        ItemPositionAdvancedSettings advanced = OldAnimationsSettings.advancedSettings;
        if (!settings.enabled) {
            return;
        }
        if (OldAnimationsSettings.globalPositions) {
                GlStateManager.translate(
                        advanced.fireballPositionX,
                        advanced.fireballPositionY,
                        advanced.fireballPositionZ
                );
                GlStateManager.rotate(advanced.fireballRotationPitch, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(advanced.fireballRotationYaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(advanced.fireballRotationRoll, 0.0f, 0.0f, 1.0f);
                GlStateManager.scale(
                    1.0f * Math.exp(advanced.fireballScale),
                    1.0f * Math.exp(advanced.fireballScale),
                    1.0f * Math.exp(advanced.fireballScale)
            );
        }
        if (OldAnimationsSettings.fireballModel) {
            RenderItem instance = Minecraft.getMinecraft().getRenderItem();
            ItemStack stack = new ItemStack(Items.fire_charge, 1, 0);
            GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            instance.renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
            ci.cancel();
        }
    }

}
