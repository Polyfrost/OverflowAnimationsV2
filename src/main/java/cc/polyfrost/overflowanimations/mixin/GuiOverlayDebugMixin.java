package cc.polyfrost.overflowanimations.mixin;
import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static net.minecraft.client.Minecraft.getDebugFPS;

@Mixin(GuiOverlayDebug.class)
public abstract class GuiOverlayDebugMixin {
    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    private static long bytesToMb(long bytes) {
        return bytes / 1024L / 1024L;
    }

    @Inject(method = "call", at = @At("HEAD"), cancellable = true)
    public void oldDebugRightLeft(CallbackInfoReturnable<List<String>> cir) {
        if (OldAnimationsSettings.oldDebugScreen && OverflowAnimations.oldAnimationsSettings.enabled) {
            BlockPos blockpos = new BlockPos(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().getEntityBoundingBox().minY, mc.getRenderViewEntity().posZ);
            Entity entity = mc.getRenderViewEntity();
            EnumFacing enumfacing = entity.getHorizontalFacing();
            Chunk chunk = mc.theWorld.getChunkFromBlockCoords(blockpos);

            List<String> list =  Lists.newArrayList("Minecraft 1.8.9 (" + getDebugFPS() + " fps" + ", " + RenderChunk.renderChunksUpdated + " chunk updates)",
                    mc.renderGlobal.getDebugInfoRenders(), mc.renderGlobal.getDebugInfoEntities(),
                    "P: " + mc.effectRenderer.getStatistics() + ". T: " + mc.theWorld.getDebugLoadedEntities(), mc.theWorld.getProviderName(), "",
                    String.format("x: %.5f (%d) // c: %d (%d)", mc.thePlayer.posX, MathHelper.floor_double(mc.thePlayer.posX), MathHelper.floor_double(mc.thePlayer.posX) >> 4, MathHelper.floor_double(mc.thePlayer.posX) & 15),
                    String.format("y: %.3f (feet pos, %.3f eyes pos)", mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posY + OverflowAnimations.eyeHeight),
                    String.format("z: %.5f (%d) // c: %d (%d)", mc.thePlayer.posZ, MathHelper.floor_double(mc.thePlayer.posZ), MathHelper.floor_double(mc.thePlayer.posZ) >> 4, MathHelper.floor_double(mc.thePlayer.posZ) & 15),
                    "f: " + (MathHelper.floor_double((double)(mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + " (" + enumfacing.toString().toUpperCase() + ") / " + MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw),
                    String.format("ws: %.3f, fs: %.3f, g: %b, fl: %.0f", mc.thePlayer.capabilities.getWalkSpeed(), mc.thePlayer.capabilities.getFlySpeed(), mc.thePlayer.onGround, mc.thePlayer.posY),
                    String.format("lc: " + chunk.getLightSubtracted(blockpos, 0) + " b: " + chunk.getBiome(blockpos, mc.theWorld.getWorldChunkManager()).biomeName) + " bl: " + chunk.getLightFor(EnumSkyBlock.BLOCK, blockpos) + " sl: " + chunk.getLightFor(EnumSkyBlock.SKY, blockpos) + " rl: " + chunk.getLightSubtracted(blockpos, 0));

            if (mc.entityRenderer != null && mc.entityRenderer.isShaderActive())
            {
                list.add("shader: " + mc.entityRenderer.getShaderGroup().getShaderGroupName());
            }
            cir.setReturnValue(list);
        }
    }

    @Inject(method = "getDebugInfoRight", at = @At("HEAD"), cancellable = true)
    public void oldDebugRightPanel(CallbackInfoReturnable<List<String>> cir) {
        if (OldAnimationsSettings.oldDebugScreen && OverflowAnimations.oldAnimationsSettings.enabled) {
            long i = Runtime.getRuntime().maxMemory();
            long j = Runtime.getRuntime().totalMemory();
            long k = Runtime.getRuntime().freeMemory();
            long l = j - k;
            List<String> list = Lists.newArrayList(String.format("Used memory: % 2d%% (%03dMB) of %03dMB", l * 100L / i, bytesToMb(l), bytesToMb(i)),
                    String.format("Allocated memory: % 2d%% (%03dMB)", j * 100L / i, bytesToMb(j)), "");
            cir.setReturnValue(list);
        }
    }
}
