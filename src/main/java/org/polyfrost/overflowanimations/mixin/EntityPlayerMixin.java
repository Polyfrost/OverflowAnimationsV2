package org.polyfrost.overflowanimations.mixin;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.polyfrost.overflowanimations.hooks.SwingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public class EntityPlayerMixin {

    //todo: investigate behavior

    @Inject(
            method = "dropItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;getEyeHeight()F"
            )
    )
    public void overflowAnimations$dropItemSwing(ItemStack droppedItem, boolean dropAround, boolean traceItem, CallbackInfoReturnable<EntityItem> cir) {
        if (!MainModSettings.INSTANCE.getOldSettings().getModernDropSwing() || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        SwingHook.swingItem();
    }

}
