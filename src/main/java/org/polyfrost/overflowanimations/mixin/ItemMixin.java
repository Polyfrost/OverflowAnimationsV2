package org.polyfrost.overflowanimations.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    //todo: do some re-equip fuckery

    @Inject(
            method = "shouldCauseReequipAnimation",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true,
            remap = false
    )
    public void overflowAnimations$modifyReequip(ItemStack oldStack, ItemStack newStack, boolean slotChanged, CallbackInfoReturnable<Boolean> ci) {
        OldAnimationsSettings settings = MainModSettings.INSTANCE.getOldSettings();
        if (((settings.getFixReequip() && !slotChanged) || settings.getDisableReequip()) && settings.enabled) {
            ci.setReturnValue(false);
        }
    }

}
