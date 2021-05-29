package club.sk1er.oldanimations.mixins;

import club.sk1er.oldanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "gg.essential.handlers.OnlineIndicator", remap = false)
public class MixinOnlineIndicator {
    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "drawTabIndicator", at = @At("HEAD"), cancellable = true, remap = false)
    private static void removeTabIndicator(CallbackInfo ci) {
        if (OldAnimationsSettings.oldTab) ci.cancel();
    }
}
