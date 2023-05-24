package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiIngame.class)
public class GuiIngameMixin {

    private final Minecraft mc = Minecraft.getMinecraft();
    @Inject(method = "showCrosshair", at = {@At("HEAD")}, cancellable = true)
    public void renderCrosshair(CallbackInfoReturnable<Boolean> cir) {
        if (OldAnimationsSettings.oldDebugCrosshair && OverflowAnimations.oldAnimationsSettings.enabled && this.mc.gameSettings.showDebugInfo &&
                !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
            cir.setReturnValue(true);
        }
    }
}