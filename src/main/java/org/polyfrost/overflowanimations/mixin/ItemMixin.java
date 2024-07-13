package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "shouldCauseReequipAnimation", at = @At("HEAD"), cancellable = true, remap = false)
    public void overflowAnimations$modifyReequip(ItemStack oldStack, ItemStack newStack, boolean slotChanged, CallbackInfoReturnable<Boolean> cir) {
        if (OldAnimationsSettings.INSTANCE.enabled) {
            if (OldAnimationsSettings.INSTANCE.itemSwitch == 0) {
                cir.setReturnValue(false);
            } else if (OldAnimationsSettings.fixReequip && OldAnimationsSettings.INSTANCE.itemSwitch != 1 && !slotChanged) {
                cir.setReturnValue(false);
            } else if (OldAnimationsSettings.INSTANCE.itemSwitch == 1) {
                cir.setReturnValue(!OldAnimationsSettings.fixReequip || slotChanged || Minecraft.getMinecraft().currentScreen instanceof GuiContainer);
            }
        }
    }

}
