package org.polyfrost.overflowanimations.mixin;

import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.SwingHook;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerSP.class)
public abstract class EntityPlayerSPMixin {

    @Inject(method = "dropOneItem", at = @At("HEAD"))
    private void dropItem(boolean dropAll, CallbackInfoReturnable<EntityItem> cir) {
        if (OldAnimationsSettings.itemThrow && OldAnimationsSettings.INSTANCE.enabled) {
            SwingHook.swingItem();
        }
    }
}
