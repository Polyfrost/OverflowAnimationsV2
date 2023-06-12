package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    public PlayerControllerMP playerController;
    @Shadow
    public MovingObjectPosition objectMouseOver;
    @Shadow
    public EffectRenderer effectRenderer;
    @Shadow
    public EntityPlayerSP thePlayer;
    @Shadow
    public WorldClient theWorld;
    @Shadow
    public GameSettings gameSettings;
    @Shadow
    private int leftClickCounter;

    @Inject(method = "sendClickBlockToController", at = @At("HEAD"))
    public void blockHitAnimation(boolean leftClick, CallbackInfo ci) {
        if (OldAnimationsSettings.oldBlockhitting && OldAnimationsSettings.punching && OldAnimationsSettings.INSTANCE.enabled && gameSettings.keyBindUseItem.isKeyDown()) {
            if (leftClickCounter <= 0 && leftClick && objectMouseOver != null && objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                if (!theWorld.isAirBlock(objectMouseOver.getBlockPos()) && (thePlayer.isAllowEdit() || !OldAnimationsSettings.adventurePunching)) {
                    if (!OldAnimationsSettings.adventureParticles && OldAnimationsSettings.punchingParticles) {
                        effectRenderer.addBlockHitEffects(objectMouseOver.getBlockPos(), objectMouseOver.sideHit);
                    }
                    if (!thePlayer.isSwingInProgress ||
                            thePlayer.swingProgressInt >= ((EntityLivingBaseInvoker) thePlayer).getArmSwingAnimation() / 2 ||
                            thePlayer.swingProgressInt < 0) {
                        thePlayer.swingProgressInt = -1;
                        thePlayer.isSwingInProgress = true;
                    }
                }
            } else
                playerController.resetBlockRemoving();
        }
    }
}