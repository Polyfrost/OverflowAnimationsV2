package org.polyfrost.overflowanimations.mixin;

import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemPotion.class)
public abstract class ItemPotionMixin {

    @Shadow public abstract int getColorFromDamage(int meta);

    @ModifyVariable(
            method = "getColorFromItemStack",
            at = @At(
                    value = "HEAD"
            ),
            argsOnly = true
    )
    private int overflowAnimations$allowPotColors(int renderPass) {
        if (!MainModSettings.INSTANCE.getOldSettings().getMilkMode() || MainModSettings.INSTANCE.getOldSettings().getColoredBottles() || !MainModSettings.INSTANCE.getOldSettings().enabled) {
            return renderPass;
        }
        return renderPass + 1;
    }

    @Inject(
            method = "getColorFromItemStack",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    public void overflowAnimations$allowPotColors(ItemStack stack, int renderPass, CallbackInfoReturnable<Integer> cir) {
        if (!MainModSettings.INSTANCE.getOldSettings().getColoredBottles() || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        if (MainModSettings.INSTANCE.getOldSettings().getMilkMode()) {
            cir.setReturnValue(renderPass > 0 ? getColorFromDamage(stack.getMetadata()) : 16777215);
        } else {
            cir.setReturnValue(getColorFromDamage(stack.getMetadata()));
        }
    }

}
