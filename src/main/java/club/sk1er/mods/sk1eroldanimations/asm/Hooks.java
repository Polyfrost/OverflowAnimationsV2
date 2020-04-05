package club.sk1er.mods.sk1eroldanimations.asm;

import club.sk1er.mods.sk1eroldanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public class Hooks {
    public static void doOldTransformations(EntityLivingBase entityLivingBase, RendererLivingEntity<?> livingEntityRenderer) {
        if (entityLivingBase instanceof EntityPlayer) {
            if (OldAnimationsSettings.oldBlocking) {
                if (((EntityPlayer) entityLivingBase).isBlocking()) {
                    if (entityLivingBase.isSneaking()) {
                        ((ModelBiped) livingEntityRenderer.getMainModel()).postRenderArm(0.0325f);
                        GlStateManager.scale(1.05f, 1.05f, 1.05f);
                        GlStateManager.translate(-0.63f, 0.30f, -0.07f);
                        GlStateManager
                                .rotate(-24405.0f, -112710, -2009900.0f, -2654900.0f);
                    } else {
                        ((ModelBiped) livingEntityRenderer.getMainModel()).postRenderArm(0.0325f);
                        GlStateManager.scale(1.05f, 1.05f, 1.05f);
                        GlStateManager.translate(-0.50f, 0.23f, -0.07f);
                        GlStateManager
                                .rotate(-24405.0f, -112710, -2009900.0f, -2654900.0f);
                    }
                } else {
                    ((ModelBiped) livingEntityRenderer.getMainModel())
                            .postRenderArm(0.0625f);
                }
            } else {
                ((ModelBiped) livingEntityRenderer.getMainModel()).postRenderArm(0.0625f);
            }

            if (!OldAnimationsSettings.oldItemHeld || ((EntityPlayer) entityLivingBase).isBlocking()) {
                GlStateManager.translate(-0.0625f, 0.4375f, 0.0625f);
            } else if (entityLivingBase.getHeldItem().getItem().isFull3D() || entityLivingBase.getHeldItem().getItem() instanceof ItemBlock) {
                GlStateManager.translate(-0.0855f, 0.4775f, 0.1585f);
                GlStateManager.rotate(-19.0f, 20.0f, 0.0f, -6.0f);
            } else {
                GlStateManager.translate(-0.03f, 0.475f, 0.0885f);
                GlStateManager.rotate(-19.0f, 5f, 5.0f, -6.0f);
            }
        } else {
            ((ModelBiped) livingEntityRenderer.getMainModel()).postRenderArm(0.0625f);
            GlStateManager.translate(-0.0625f, 0.4375f, 0.0625f);
        }
    }
    public static void swingIfNecessary() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (!OldAnimationsSettings.punching || !Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown() || Minecraft.getMinecraft().objectMouseOver == null || Minecraft.getMinecraft().objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) return;
        if (!player.isSwingInProgress || player.swingProgressInt >= player.getArmSwingAnimationEnd() / 2 || player.swingProgressInt < 0) {
            player.swingProgressInt = -1;
            player.isSwingInProgress = true;
        }
    }
    public static void doOldEat(AbstractClientPlayer clientPlayer, float partialTicks, ItemStack itemToRender) {
        float f = (float)clientPlayer.getItemInUseCount() - partialTicks + 1.0F;
        float f1 = f / (float)itemToRender.getMaxItemUseDuration();
        float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * (float)Math.PI) * 0.1F);

        if (f1 >= 0.8F)
        {
            f2 = 0.0F;
        }

        GlStateManager.translate(0.0F, f2, 0.0F);
        float f3 = 1.0F - (float)Math.pow(f1, 27.0D);
        GlStateManager.translate(f3 * 0.72F, f3 * -0.5F, f3 * 0.08F);
        GlStateManager.rotate(f3 * 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f3 * 4.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f3 * 33.0F, 0.0F, 0.0F, 1.0F);
    }
}
