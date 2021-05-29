package club.sk1er.oldanimations.mixins;

import club.sk1er.oldanimations.config.OldAnimationsSettings;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.GuiIngameForge;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiIngameForge.class)
public class GuiIngameForgeMixin {
    @Redirect(method = "renderHUDText", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/GuiIngameForge;drawRect(IIIII)V"))
    private void cancelBackgroundDrawing(int left, int top, int right, int bottom, int color) {
        if (!OldAnimationsSettings.oldDebugScreen) GuiIngameForge.drawRect(left, top, right, bottom, color);
    }

    @Redirect(method = "renderHUDText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"))
    private int removeShadow(FontRenderer fontRenderer, String text, int x, int y, int color) {
        return fontRenderer.drawString(text, x, y, color, OldAnimationsSettings.oldDebugScreen);
    }

    @ModifyVariable(method = "renderHealth", at = @At(value = "LOAD", opcode = Opcodes.ILOAD, ordinal = 1), index = 5, remap = false)
    private boolean cancelFlash(boolean original) {
        return original && !OldAnimationsSettings.oldHealth;
    }
}
