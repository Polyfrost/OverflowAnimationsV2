package cc.polyfrost.overflowanimations.mixin;
import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerSP.class)

public abstract class EntityPlayerSPMixin {
    @Final
    private final Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "dropOneItem", at = @At("HEAD"))
    private void dropItem(boolean dropAll, CallbackInfoReturnable<EntityItem> cir) {
        if (OldAnimationsSettings.itemThrow && OverflowAnimations.oldAnimationsSettings.enabled) {
            if (!mc.thePlayer.isSwingInProgress || mc.thePlayer.swingProgressInt >= ((EntityLivingBaseInvoker) mc.thePlayer).getArmSwingAnimation() / 2 ||
                    mc.thePlayer.swingProgressInt < 0) {
                mc.thePlayer.swingProgressInt = -1;
                mc.thePlayer.isSwingInProgress = true;
            }
        }
    }
}
