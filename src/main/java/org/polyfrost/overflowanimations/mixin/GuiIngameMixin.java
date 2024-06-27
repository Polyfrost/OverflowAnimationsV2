package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.gui.GuiIngame;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GuiIngame.class, priority = 1001)
public class GuiIngameMixin {

    @Inject(method = "showCrosshair", at = @At("HEAD"), cancellable = true)
    public void overflowAnimations$renderCrosshair(CallbackInfoReturnable<Boolean> cir) {
        if (OldAnimationsSettings.INSTANCE.debugCrosshairMode == 0 && OldAnimationsSettings.INSTANCE.enabled) {
            cir.setReturnValue(true);
        }
    }

}
