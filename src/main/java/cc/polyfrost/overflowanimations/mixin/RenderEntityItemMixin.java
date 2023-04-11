package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderEntityItem.class)
public abstract class RenderEntityItemMixin {
    @Unique
    private boolean overflow$shouldReset = false;

    @Redirect(method = "func_177077_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V"))
    private void redirect(float angle, float x, float y, float z, EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_) {
        if (p_177077_9_.isGui3d() || !OverflowAnimations.oldAnimationsSettings.enabled || !OldAnimationsSettings.items2D) {
            GlStateManager.rotate(angle, x, y, z);
        } else {
            GlStateManager.rotate(180.0F - (Minecraft.getMinecraft().getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(((Minecraft.getMinecraft()).gameSettings.thirdPersonView == 2) ? (Minecraft.getMinecraft().getRenderManager()).playerViewX : -(Minecraft.getMinecraft().getRenderManager()).playerViewX, 1.0F, 0.0F, 0.0F);
        }
    }

    @ModifyArg(method = {"doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeHooksClient;handleCameraTransforms(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)Lnet/minecraft/client/resources/model/IBakedModel;"), index = 1)
    private ItemCameraTransforms.TransformType stopItemModelRender(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
        if (OldAnimationsSettings.items2D && OldAnimationsSettings.itemSprites && !model.isGui3d() && OverflowAnimations.oldAnimationsSettings.enabled) {
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableCull();
            GlStateManager.enableAlpha();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            overflow$shouldReset = true;
            return ItemCameraTransforms.TransformType.GUI;
        }
        return cameraTransformType;
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", shift = At.Shift.AFTER))
    private void resetItemModelRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (overflow$shouldReset) {
            GlStateManager.enableCull();
            GlStateManager.disableAlpha();
            RenderHelper.enableStandardItemLighting();
        }
    }
}