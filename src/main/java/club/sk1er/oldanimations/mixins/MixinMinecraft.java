package club.sk1er.oldanimations.mixins;

import club.sk1er.oldanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "rightClickMouse", at = @At("HEAD"))
    public void rightClickMouse(CallbackInfo ci) {
        if (OldAnimationsSettings.punching &&
                Minecraft.getMinecraft().playerController.getIsHittingBlock() &&
                Minecraft.getMinecraft().thePlayer.getHeldItem() != null &&
                (Minecraft.getMinecraft().thePlayer.getHeldItem().getItemUseAction() != EnumAction.NONE ||
                        Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof ItemBlock)) {
            // This sends packets to the server saying we stopped breaking.
            // Simply making getIsHittingBlock return false will make the server
            // think we are still breaking the block while right clicking.
            // Which is bad. Obviously.
            Minecraft.getMinecraft().playerController.resetBlockRemoving();
        }
    }

}
