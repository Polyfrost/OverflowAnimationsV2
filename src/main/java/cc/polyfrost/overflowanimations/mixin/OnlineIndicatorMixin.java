package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "gg.essential.handlers.OnlineIndicator", remap = false)
public class OnlineIndicatorMixin {
    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "drawTabIndicator", at = @At("HEAD"), cancellable = true, remap = false)
    private static void removeTabIndicator(CallbackInfo ci) {
        if (OverflowAnimations.oldAnimationsSettings.tabMode == 0 ||
                OverflowAnimations.oldAnimationsSettings.tabMode == 1 && OverflowAnimations.oldAnimationsSettings.enabled) ci.cancel();
    }
}
