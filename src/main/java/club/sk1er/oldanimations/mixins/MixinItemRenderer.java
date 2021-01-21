package club.sk1er.oldanimations.mixins;

import club.sk1er.oldanimations.AnimationHandler;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
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

}
