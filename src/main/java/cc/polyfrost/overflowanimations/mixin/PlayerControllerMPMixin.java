package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class PlayerControllerMPMixin {

    @Shadow private boolean isHittingBlock;

    @Shadow @Final private Minecraft mc;

    @Redirect(method = "resetBlockRemoving", at = @At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;isHittingBlock:Z", ordinal = 0))
    private boolean cancelHit2(PlayerControllerMP instance) {
        return OldAnimationsSettings.breakFix || isHittingBlock;
    }

    @Redirect(method = "resetBlockRemoving", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/NetHandlerPlayClient;addToSendQueue(Lnet/minecraft/network/Packet;)V"))
    private void cancelHit3(NetHandlerPlayClient instance, Packet<?> p_147297_1_) {
        if (isHittingBlock)
            mc.getNetHandler().addToSendQueue(p_147297_1_);
    }

    @ModifyArg(method = {"clickBlock", "onPlayerDamageBlock"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;sendBlockBreakProgress(ILnet/minecraft/util/BlockPos;I)V"), index = 2)
    public int fixSyncProgress(int par1) {
        return par1 + (OldAnimationsSettings.breakFix ? 1 : 0);
    }

    @Inject(method = "getIsHittingBlock", at = @At("HEAD"), cancellable = true)
    private void cancelHit(CallbackInfoReturnable<Boolean> cir) {
        if (OldAnimationsSettings.oldBlockhitting && OldAnimationsSettings.punching && OldAnimationsSettings.INSTANCE.enabled)
            cir.setReturnValue(false);
    }
}