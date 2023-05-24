package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class PlayerControllerMPMixin {
    @Final
    @Shadow
    private final Minecraft mc = Minecraft.getMinecraft();
    @Mutable
    @Final
    @Shadow
    private final NetHandlerPlayClient netClientHandler;
    @Shadow
    private BlockPos currentBlock = new BlockPos(-1, -1, -1);
    @Shadow
    private boolean isHittingBlock;
    @Shadow
    private float curBlockDamageMP;

    protected PlayerControllerMPMixin(NetHandlerPlayClient netClientHandler) {
        this.netClientHandler = netClientHandler;
    }

    /**
     * @author mixces
     * @reason bug fix
     */
    @Overwrite
    public void resetBlockRemoving() {
        if (isHittingBlock)
            mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK,
                    currentBlock, EnumFacing.DOWN));
        isHittingBlock = false;
        curBlockDamageMP = 0.0F;
        mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityId(), currentBlock, -1);
    }

    @Inject(method = "getIsHittingBlock", at = @At("HEAD"), cancellable = true)
    private void cancelHit(CallbackInfoReturnable<Boolean> cir) {
        if (OldAnimationsSettings.oldBlockhitting && OldAnimationsSettings.punching && OverflowAnimations.oldAnimationsSettings.enabled)
            cir.setReturnValue(false);
    }
}