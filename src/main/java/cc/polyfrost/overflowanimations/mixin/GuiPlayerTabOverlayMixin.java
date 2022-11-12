package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = GuiPlayerTabOverlay.class, priority = 999)
public class GuiPlayerTabOverlayMixin {

    @ModifyVariable(method = "renderPlayerlist", at = @At("STORE"), index = 11)
    private boolean checkTabSetting(boolean original) {
        if (!OverflowAnimations.oldAnimationsSettings.enabled) {
            return original;
        }

        return !OldAnimationsSettings.oldTab && original;
    }
}
