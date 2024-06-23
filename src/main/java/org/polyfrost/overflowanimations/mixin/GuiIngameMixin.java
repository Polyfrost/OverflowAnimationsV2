package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.gui.GuiIngame;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiIngame.class)
public class GuiIngameMixin {

    @Inject(
            method = "showCrosshair",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    public void overflowAnimations$renderCrosshair(CallbackInfoReturnable<Boolean> cir) {
        if (MainModSettings.INSTANCE.getOldSettings().getDebugCrosshairMode() != 0 || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        cir.setReturnValue(true);
    }

}
