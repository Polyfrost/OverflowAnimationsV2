package club.sk1er.oldanimations.mixins;

import club.sk1er.oldanimations.AnimationHandler;
import club.sk1er.oldanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

    @Shadow
    private ItemStack itemToRender;

    @Shadow
    private float prevEquippedProgress;

    @Shadow
    private float equippedProgress;

    @Shadow private int equippedItemSlot;

    @Inject(method = "renderItemInFirstPerson", at = @At("HEAD"), cancellable = true)
    public void renderItemInFirstPerson(float partialTicks, CallbackInfo ci) {
        if (itemToRender != null) {
            ItemRenderer $this = (ItemRenderer) (Object) this;
            float equipProgress = prevEquippedProgress + (equippedProgress - prevEquippedProgress) * partialTicks;
            if (AnimationHandler.getInstance().renderItemInFirstPerson($this, itemToRender, equipProgress, partialTicks)) {
                ci.cancel();
            }
        }
    }

    @ModifyArg(method = "updateEquippedItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/MathHelper;clamp_float(FFF)F"), index = 0)
    private float handleItemSwitch(float original) {
        EntityPlayer entityplayer = Minecraft.getMinecraft().thePlayer;
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (OldAnimationsSettings.itemSwitch && this.equippedItemSlot == entityplayer.inventory.currentItem && ItemStack.areItemsEqual(this.itemToRender, itemstack)) {
            return 1.0f - this.equippedProgress;
        }
        return original;
    }
}
