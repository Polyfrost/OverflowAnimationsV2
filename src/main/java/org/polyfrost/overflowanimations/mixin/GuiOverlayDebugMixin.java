package org.polyfrost.overflowanimations.mixin;

import org.polyfrost.overflowanimations.config.MainModSettings;
import org.polyfrost.overflowanimations.hooks.DebugOverlayHook;
import net.minecraft.client.gui.GuiOverlayDebug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(GuiOverlayDebug.class)
public abstract class GuiOverlayDebugMixin {

    @Inject(
            method = "call",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    public void overflowAnimations$oldDebugLeft(CallbackInfoReturnable<List<String>> cir) {
        if (MainModSettings.INSTANCE.getOldSettings().getDebugScreenMode() != 0 || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        cir.setReturnValue(DebugOverlayHook.getDebugInfoLeft());
    }

    @Inject(
            method = "getDebugInfoRight",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    public void overflowAnimations$oldDebugRight(CallbackInfoReturnable<List<String>> cir) {
        if (MainModSettings.INSTANCE.getOldSettings().getDebugScreenMode() != 0 || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        cir.setReturnValue(DebugOverlayHook.getDebugInfoRight());
    }

}
