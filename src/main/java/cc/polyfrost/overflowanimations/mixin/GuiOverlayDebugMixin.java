package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import cc.polyfrost.overflowanimations.hooks.DebugOverlayHook;
import net.minecraft.client.gui.GuiOverlayDebug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(GuiOverlayDebug.class)
public abstract class GuiOverlayDebugMixin {

    @Inject(method = "call", at = @At("HEAD"), cancellable = true)
    public void oldDebugLeft(CallbackInfoReturnable<List<String>> cir) {
        if (OldAnimationsSettings.oldDebugScreen && OldAnimationsSettings.INSTANCE.enabled) {
            cir.setReturnValue(DebugOverlayHook.getDebugInfoLeft());
        }
    }

    @Inject(method = "getDebugInfoRight", at = @At("HEAD"), cancellable = true)
    public void oldDebugRight(CallbackInfoReturnable<List<String>> cir) {
        if (OldAnimationsSettings.oldDebugScreen && OldAnimationsSettings.INSTANCE.enabled) {
            cir.setReturnValue(DebugOverlayHook.getDebugInfoRight());
        }
    }
}
