package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.GuiIngameForge;
import org.objectweb.asm.Opcodes;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.DebugCrosshairHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameForge.class)
public class GuiIngameForgeMixin extends GuiIngame {

    //todo: see if i can change the redirects

    @Shadow(remap = false) private ScaledResolution res;

    public GuiIngameForgeMixin(Minecraft minecraft) {
        super(minecraft);
    }

    @Redirect(
            method = "renderHUDText",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/client/GuiIngameForge;drawRect(IIIII)V"
            )
    )
    private void overflowAnimations$cancelBackgroundDrawing(int left, int top, int right, int bottom, int color) {
        OldAnimationsSettings settings = MainModSettings.INSTANCE.getOldSettings();
        if (!settings.enabled || (settings.getDebugScreenMode() != 0 && settings.getDebugScreenMode() != 2)) {
            GuiIngameForge.drawRect(left, top, right, bottom, color);
        }
    }

    @Redirect(
            method = "renderHUDText",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"
            )
    )
    private int overflowAnimations$removeShadow(FontRenderer fontRenderer, String text, int x, int y, int color) {
        OldAnimationsSettings settings = MainModSettings.INSTANCE.getOldSettings();
        return fontRenderer.drawString(text, x, y, color, (settings.getDebugScreenMode() == 0 || settings.getDebugScreenMode() == 2) && settings.enabled);
    }

    @ModifyVariable(
            method = "renderHealth",
            at = @At(
                    value = "LOAD",
                    ordinal = 1
            ),
            index = 5,
            remap = false
    )
    private boolean overflowAnimations$cancelFlash(boolean original) {
        return (!MainModSettings.INSTANCE.getOldSettings().getOldHealth() || !MainModSettings.INSTANCE.getOldSettings().enabled) && original;
    }

    @Inject(
            method = "renderGameOverlay",
            at = @At(
                    value = "FIELD",
                    opcode = Opcodes.GETSTATIC,
                    target = "Lnet/minecraftforge/client/GuiIngameForge;renderCrosshairs:Z"
            )
    )
    public void overflowAnimations$renderRGBCrosshair(float partialTicks, CallbackInfo ci) {
        if (MainModSettings.INSTANCE.getOldSettings().getDebugCrosshairMode() != 2 || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        if (mc.gameSettings.showDebugInfo && !mc.thePlayer.hasReducedDebug() && !mc.gameSettings.reducedDebugInfo) {
            DebugCrosshairHook.INSTANCE.renderDirections(partialTicks, zLevel, res, mc);
        }
    }

}
