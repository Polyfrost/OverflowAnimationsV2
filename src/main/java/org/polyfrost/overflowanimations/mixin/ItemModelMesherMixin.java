package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.SkullModelHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemModelMesher.class)
public class ItemModelMesherMixin {

    @Inject(
            method = "getItemModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/resources/model/IBakedModel;",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void mixcesAnimations$useCustomBlockModel(ItemStack stack, CallbackInfoReturnable<IBakedModel> cir) {
        if (stack == null) return;
        if (!OldAnimationsSettings.oldSkulls || !OldAnimationsSettings.INSTANCE.enabled) return;
        if (stack.getItem() instanceof ItemSkull) {
            cir.setReturnValue(SkullModelHook.INSTANCE.getSkullModel(stack));
        }
    }

}