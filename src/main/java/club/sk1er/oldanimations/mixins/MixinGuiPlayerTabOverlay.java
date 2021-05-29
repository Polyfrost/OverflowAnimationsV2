package club.sk1er.oldanimations.mixins;

import club.sk1er.oldanimations.config.OldAnimationsSettings;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiPlayerTabOverlay.class, priority = 999)
public class MixinGuiPlayerTabOverlay {

    @ModifyVariable(method = "renderPlayerlist", at = @At("STORE"), index = 11)
    private boolean checkTabSetting(boolean original) {
        return !OldAnimationsSettings.oldTab && original;
    }

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "handler$renderEssentialIndicator$zzi000", at = @At("HEAD"), cancellable = true, remap = false)
    private void removeEssentialIndicator(CallbackInfo ci) {
        if (OldAnimationsSettings.oldTab) ci.cancel();
    }
}
