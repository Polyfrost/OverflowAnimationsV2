package club.sk1er.oldanimations.mixins;

import club.sk1er.oldanimations.AnimationHandler;
import club.sk1er.oldanimations.config.OldAnimationsSettings;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

    @Shadow
    private ItemStack itemToRender;

    @Shadow
    private float prevEquippedProgress;

    @Shadow
    private float equippedProgress;

    @Inject(method="renderItemInFirstPerson", at=@At("HEAD"), cancellable = true)
    public void renderItemInFirstPerson(float partialTicks, CallbackInfo ci) {
        if(itemToRender != null) {
            ItemRenderer $this = (ItemRenderer)(Object)this;
            float equipProgress = prevEquippedProgress + (equippedProgress - prevEquippedProgress) * partialTicks;
            if(AnimationHandler.getInstance().renderItemInFirstPerson($this, itemToRender, equipProgress, partialTicks)) {
                ci.cancel();
            }
        }
    }


    @Inject(method="resetEquippedProgress", at=@At("HEAD"), cancellable = true)
    public void resetEquippedProgress(CallbackInfo ci) {
        if(OldAnimationsSettings.itemSwitch) {
            ci.cancel();
        }
    }

    @Inject(method="resetEquippedProgress2", at=@At("HEAD"), cancellable = true)
    public void resetEquippedProgress2(CallbackInfo ci) {
        if(OldAnimationsSettings.itemSwitch) {
            ci.cancel();
        }
    }

}
