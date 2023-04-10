package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {

    @Final
    private final Minecraft mc = Minecraft.getMinecraft();

    private int getArmSwingAnimationEnd(EntityPlayerSP player) {
        return player.isPotionActive(Potion.digSpeed) ? 5 - player.getActivePotionEffect(Potion.digSpeed).getAmplifier() :
                (player.isPotionActive(Potion.digSlowdown) ? 8 + player.getActivePotionEffect(Potion.digSlowdown).getAmplifier() * 2 : 6);
    }

    @Inject(method = "spawnEntityInWorld", at = @At("HEAD"))
    private void entityThrow(Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        final EntityPlayerSP player = mc.thePlayer;
        if (OldAnimationsSettings.itemThrow && OverflowAnimations.oldAnimationsSettings.enabled && entityIn instanceof EntityThrowable) {
            if (!mc.thePlayer.isSwingInProgress || mc.thePlayer.swingProgressInt >= this.getArmSwingAnimationEnd(player) / 2 ||
                    mc.thePlayer.swingProgressInt < 0) {
                mc.thePlayer.swingProgressInt = -1;
                mc.thePlayer.isSwingInProgress = true;
            }
        }
    }
}