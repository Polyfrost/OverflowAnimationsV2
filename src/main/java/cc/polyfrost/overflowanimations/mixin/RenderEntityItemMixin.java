package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderEntityItem.class)
public abstract class RenderEntityItemMixin {

    @Unique
    private boolean overflowanimations$isGui3d;

    @ModifyVariable(method = "func_177077_a", at = @At("STORE"), ordinal = 0)
    private boolean hookGui3d(boolean isGui3d) {
        overflowanimations$isGui3d = isGui3d;
        return isGui3d;
    }

    @ModifyArg(method = "func_177077_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V"), index = 0)
    private float apply2dItem(float angle) {
        if (!overflowanimations$isGui3d && OldAnimationsSettings.items2D && OldAnimationsSettings.INSTANCE.enabled) {
            return 180.0F - (Minecraft.getMinecraft().getRenderManager()).playerViewY;

        }
        return angle;
    }

    @Inject(method = "func_177077_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", shift = At.Shift.AFTER))
    private void fix2dRotation(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_, CallbackInfoReturnable<Integer> cir) {
        if (!overflowanimations$isGui3d && OldAnimationsSettings.items2D && OldAnimationsSettings.INSTANCE.enabled && OldAnimationsSettings.rotationFix) {
            GlStateManager.rotate(((Minecraft.getMinecraft()).gameSettings.thirdPersonView == 2) ? (Minecraft.getMinecraft().getRenderManager()).playerViewX : -(Minecraft.getMinecraft().getRenderManager()).playerViewX, 1.0F, 0.0F, 0.0F);
        }
    }

    @Redirect(method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V"))
    public void changeTransform(RenderItem instance, ItemStack stack, IBakedModel model) {
        if (OldAnimationsSettings.items2D && OldAnimationsSettings.itemSprites && OldAnimationsSettings.INSTANCE.enabled && !model.isGui3d() && !model.isBuiltInRenderer()) {
            GlStateManager.disableLighting();
            GlStateManager.scale(1.0F, 1.0F, 0.0F);
            instance.renderItem(stack, ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND));
            GlStateManager.enableLighting();
        } else {
            instance.renderItem(stack, model);
        }
    }
}