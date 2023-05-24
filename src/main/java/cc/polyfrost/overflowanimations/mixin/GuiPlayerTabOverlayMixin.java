package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import com.google.common.collect.Ordering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static net.minecraft.client.gui.Gui.drawRect;

@Mixin(value = GuiPlayerTabOverlay.class, priority = 999)
public abstract class GuiPlayerTabOverlayMixin {

    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    @Final
    private static Ordering<NetworkPlayerInfo> field_175252_a;
    @Shadow
    protected abstract void drawPing(int i, int j, int k, NetworkPlayerInfo networkPlayerInfoIn);

    @Inject(method = "renderPlayerlist", at = @At("HEAD"), cancellable = true)
    public void renderOldTab(int width, Scoreboard scoreboardIn, ScoreObjective var37, CallbackInfo ci) {
        if (OverflowAnimations.oldAnimationsSettings.tabMode == 0 && OverflowAnimations.oldAnimationsSettings.enabled) {
            ci.cancel();
            FontRenderer var8 = mc.fontRendererObj;
            NetHandlerPlayClient nethandlerplayclient = mc.thePlayer.sendQueue;
            List<NetworkPlayerInfo> var42 = field_175252_a.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
            int var15 = mc.thePlayer.sendQueue.currentServerMaxPlayers;
            int var16 = var15;
            ScaledResolution scaledresolution = new ScaledResolution(mc);
            int var17;
            int var6 = scaledresolution.getScaledWidth();
            int var21;
            int var22;
            int var23;
            for (var17 = 1; var16 > 20; var16 = (var15 + var17 - 1) / var17) {
                ++var17;
            }
            int var46 = 300 / var17;
            if (var46 > 150) {
                var46 = 150;
            }
            int var19 = (var6 - var17 * var46) / 2;
            byte var47 = 10;
            drawRect(var19 - 1, var47 - 1, var19 + var46 * var17, var47 + 9 * var16, Integer.MIN_VALUE);
            for (var21 = 0; var21 < var15; ++var21) {
                var22 = var19 + var21 % var17 * var46;
                var23 = var47 + var21 / var17 * 9;
                drawRect(var22, var23, var22 + var46 - 1, var23 + 8, 553648127);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableAlpha();
                if (var21 < var42.size()) {
                    NetworkPlayerInfo var48 = var42.get(var21);
                    ScorePlayerTeam var49 = mc.theWorld.getScoreboard().getPlayersTeam(var48.getGameProfile().getName());
                    String var50 = ScorePlayerTeam.formatPlayerName(var49, var48.getGameProfile().getName());
                    var8.drawStringWithShadow(var50, var22, var23, 16777215);
                    if (var37 != null) {
                        int var27 = var22 + var8.getStringWidth(var50) + 5;
                        int var28 = var22 + var46 - 12 - 5;
                        if (var28 - var27 > 5) {
                            Score var29 = var37.getScoreboard().getValueFromObjective(var48.getGameProfile().getName(), var37);
                            String var30 = EnumChatFormatting.YELLOW + "" + var29.getScorePoints();
                            var8.drawStringWithShadow(var30, var28 - var8.getStringWidth(var30), var23, 16777215);
                        }
                    }
                    drawPing(50, var22 + var46 - 52, var23, var48);
                }
            }
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.enableAlpha();
        }
    }

    @ModifyVariable(method = "renderPlayerlist", at = @At("STORE"), index = 11)
    private boolean checkTabSetting(boolean original) {
        return (OverflowAnimations.oldAnimationsSettings.tabMode != 2 || !OverflowAnimations.oldAnimationsSettings.enabled) && original;
    }
}
