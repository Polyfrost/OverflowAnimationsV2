package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "gg.essential.handlers.OnlineIndicator", remap = false)
public class OnlineIndicatorMixin {
    @Dynamic("Essential")
    @Inject(method = "drawTabIndicator", at = @At("HEAD"), cancellable = true, remap = false)
    private static void removeTabIndicator(CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.tabMode == 0 ||
                OldAnimationsSettings.INSTANCE.tabMode == 1 && OldAnimationsSettings.INSTANCE.enabled)
            ci.cancel();
    }
}
