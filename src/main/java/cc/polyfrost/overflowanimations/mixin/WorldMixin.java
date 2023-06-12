package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
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

    @Inject(method = "spawnEntityInWorld", at = @At("HEAD"))
    private void entityThrow(Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        if (OldAnimationsSettings.itemThrow && OldAnimationsSettings.INSTANCE.enabled && entityIn instanceof EntityThrowable) {
            if (!mc.thePlayer.isSwingInProgress || mc.thePlayer.swingProgressInt >= ((EntityLivingBaseInvoker) mc.thePlayer).getArmSwingAnimation() / 2 ||
                    mc.thePlayer.swingProgressInt < 0) {
                mc.thePlayer.swingProgressInt = -1;
                mc.thePlayer.isSwingInProgress = true;
            }
        }
    }
}