package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.BlockPos;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerControllerMP.class)
public abstract class PlayerControllerMPMixin {

    //todo: overhaul

    @Shadow protected abstract boolean isHittingPosition(BlockPos pos);
    @Shadow public abstract boolean getIsHittingBlock();

    //todo: create modern block break progress feature
    @ModifyArg(
            method = {"clickBlock", "onPlayerDamageBlock"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/WorldClient;sendBlockBreakProgress(ILnet/minecraft/util/BlockPos;I)V"
            ),
            index = 2
    )
    public int overflowAnimations$fixDelay(int par1) {
        return par1 + (MainModSettings.INSTANCE.getOldSettings().getBreakFix() && MainModSettings.INSTANCE.getOldSettings().enabled ? 1 : 0);
    }

    @Redirect(
            method = "onPlayerDamageBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;isHittingPosition(Lnet/minecraft/util/BlockPos;)Z"
            )
    )
    public boolean overflowAnimations$fixLogic(PlayerControllerMP instance, BlockPos pos) {
        return MainModSettings.INSTANCE.getOldSettings().getBreakFix() && MainModSettings.INSTANCE.getOldSettings().enabled ? isHittingPosition(pos) && getIsHittingBlock() : isHittingPosition(pos);
    }

}
