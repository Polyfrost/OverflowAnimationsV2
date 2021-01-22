package club.sk1er.oldanimations.mixins;

import club.sk1er.oldanimations.AnimationHandler;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class MixinRenderItem {

    @Unique
    private EntityLivingBase lastEntityToRenderFor = null;

    @Inject(method = "renderItemModelForEntity", at = @At("HEAD"))
    public void renderItemModelForEntity(ItemStack stack, EntityLivingBase entityToRenderFor,
                                         ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        lastEntityToRenderFor = entityToRenderFor;
    }

    @Redirect(method = "renderItemModelTransform", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraftforge/client/ForgeHooksClient;handleCameraTransforms(" +
            "Lnet/minecraft/client/resources/model/IBakedModel;" +
            "Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)" +
            "Lnet/minecraft/client/resources/model/IBakedModel;",
        remap = false)
    )
    public IBakedModel renderItemModelTransform_transform(IBakedModel model, ItemCameraTransforms.TransformType type) {
        model = ForgeHooksClient.handleCameraTransforms(model, type);
        if (type == ItemCameraTransforms.TransformType.THIRD_PERSON &&
            lastEntityToRenderFor instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) lastEntityToRenderFor;
            ItemStack heldStack = p.getHeldItem();
            if (heldStack != null && p.getItemInUseCount() > 0 &&
                heldStack.getItemUseAction() == EnumAction.BLOCK) {
                AnimationHandler.getInstance().doSwordBlock3rdPersonTransform();
            }
        }
        return model;
    }

}
