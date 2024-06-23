package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiIngame.class)
public class GuiIngameMixin extends Gui {

    @Shadow @Final protected Minecraft mc;

    @Inject(method = "showCrosshair", at = @At("HEAD"), cancellable = true)
    public void overflowAnimations$renderCrosshair(CallbackInfoReturnable<Boolean> cir) {
        if (OldAnimationsSettings.INSTANCE.debugCrosshairMode == 0 && OldAnimationsSettings.INSTANCE.enabled && mc.gameSettings.showDebugInfo) {
            cir.setReturnValue(true);
        }
    }

}
