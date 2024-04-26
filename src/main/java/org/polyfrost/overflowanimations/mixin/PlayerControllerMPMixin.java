package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.BlockPos;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerControllerMP.class)
public abstract class PlayerControllerMPMixin {

    @Shadow protected abstract boolean isHittingPosition(BlockPos pos);
    @Shadow public abstract boolean getIsHittingBlock();

    @ModifyArg(method = {"clickBlock", "onPlayerDamageBlock"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;sendBlockBreakProgress(ILnet/minecraft/util/BlockPos;I)V"), index = 2)
    public int overflowAnimations$fixDelay(int par1) {
        return par1 + (OldAnimationsSettings.breakFix && OldAnimationsSettings.INSTANCE.enabled ? 1 : 0);
    }

    @Redirect(method = "onPlayerDamageBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;isHittingPosition(Lnet/minecraft/util/BlockPos;)Z"))
    public boolean overflowAnimations$fixLogic(PlayerControllerMP instance, BlockPos pos) {
        return OldAnimationsSettings.breakFix && OldAnimationsSettings.INSTANCE.enabled ? isHittingPosition(pos) && getIsHittingBlock() : isHittingPosition(pos);
    }

}
