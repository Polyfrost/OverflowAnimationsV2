package club.sk1er.oldanimations.mixins;

import club.sk1er.oldanimations.config.OldAnimationsSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin extends EntityLivingBase {
    @Unique
    private float currentHeight = 1.62F;
    @Unique
    private long lastMillis = System.currentTimeMillis();

    public EntityPlayerMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "getEyeHeight", at = @At("HEAD"), cancellable = true)
    private void modifyEyeHeight(CallbackInfoReturnable<Float> cir) {
        if (!OldAnimationsSettings.oldSneaking) return;

        final int delay = 1000 / 100;
        if (isSneaking()) {
            final float sneakingHeight = 1.54F;
            if (currentHeight > sneakingHeight) {
                final long time = System.currentTimeMillis();
                final long timeSinceLastChange = time - lastMillis;
                if (timeSinceLastChange > delay) {
                    currentHeight -= 0.012F;
                    lastMillis = time;
                }
            }
        } else {
            final float standingHeight = 1.62F;
            if (currentHeight < standingHeight && currentHeight > 0.2F) {
                final long time = System.currentTimeMillis();
                final long timeSinceLastChange = time - lastMillis;
                if (timeSinceLastChange > delay) {
                    currentHeight += 0.012F;
                    lastMillis = time;
                }
            } else {
                currentHeight = 1.62F;
            }
        }

        cir.setReturnValue(currentHeight);
    }
}
