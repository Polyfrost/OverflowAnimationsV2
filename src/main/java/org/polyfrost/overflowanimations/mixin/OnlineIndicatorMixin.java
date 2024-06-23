package org.polyfrost.overflowanimations.mixin;

import org.polyfrost.overflowanimations.config.MainModSettings;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "gg.essential.handlers.OnlineIndicator", remap = false)
public class OnlineIndicatorMixin {

    //todo: i have no clue

    @Dynamic("Essential")
    @Inject(
            method = "drawTabIndicator",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true,
            remap = false
    )
    private static void overflowAnimations$removeTabIndicator(CallbackInfo ci) {
        if (MainModSettings.INSTANCE.getOldSettings().getTabMode() != 2 || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        ci.cancel();
    }

}
