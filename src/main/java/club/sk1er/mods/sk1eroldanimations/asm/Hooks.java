package club.sk1er.mods.sk1eroldanimations.asm;

import club.sk1er.mods.sk1eroldanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

@SuppressWarnings("unused")
public class Hooks {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void doOldTransformations(EntityLivingBase entityLivingBase, RendererLivingEntity<?> livingEntityRenderer) {
        final ModelBiped model = (ModelBiped) livingEntityRenderer.getMainModel();
        if (entityLivingBase instanceof EntityPlayer) {
            if (OldAnimationsSettings.oldBlocking) {
                if (((EntityPlayer) entityLivingBase).isBlocking()) {
                    if (entityLivingBase.isSneaking()) {
                        model.postRenderArm(0.0325f);
                        GlStateManager.scale(1.05f, 1.05f, 1.05f);
                        GlStateManager.translate(-0.63f, 0.30f, -0.07f);
                        GlStateManager.rotate(-24405.0f, -112710, -2009900.0f, -2654900.0f);
                    } else {
                        model.postRenderArm(0.0325f);
                        GlStateManager.scale(1.05f, 1.05f, 1.05f);
                        GlStateManager.translate(-0.50f, 0.23f, -0.07f);
                        GlStateManager.rotate(-24405.0f, -112710, -2009900.0f, -2654900.0f);
                    }
                } else {
                    model.postRenderArm(0.0625f);
                }
            } else {
                model.postRenderArm(0.0625f);
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
            model.postRenderArm(0.0625f);
            GlStateManager.translate(-0.0625f, 0.4375f, 0.0625f);
        }
    }

    public static void swingIfNecessary() {
        final EntityPlayer player = mc.thePlayer;
        if (!OldAnimationsSettings.punching || !mc.gameSettings.keyBindAttack.isKeyDown() || mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)
            return;

        if (!player.isSwingInProgress || player.swingProgressInt >= player.getArmSwingAnimationEnd() / 2 || player.swingProgressInt < 0) {
            player.swingProgressInt = -1;
            player.isSwingInProgress = true;
        }
    }

    public static void doOldEat(AbstractClientPlayer clientPlayer, float partialTicks, ItemStack itemToRender) {
        final float f = (float) clientPlayer.getItemInUseCount() - partialTicks + 1.0F;
        final float f1 = f / (float) itemToRender.getMaxItemUseDuration();
        float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * (float) Math.PI) * 0.1F);

        if (f1 >= 0.8F) {
            f2 = 0.0F;
        }

        GlStateManager.translate(0.0F, f2, 0.0F);
        final float f3 = 1.0F - (float) Math.pow(f1, 27.0D);
        GlStateManager.translate(f3 * 0.69F, f3 * -0.54F, f3 * 0.0F);
        GlStateManager.rotate(f3 * 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f3 * 4.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f3 * 33.0F, 0.0F, 0.0F, 1.0F);
    }

    public static void doOldItemTransformation(ItemStack itemToRender) {
        final int itemID = Item.getIdFromItem(itemToRender.getItem());
        if (OldAnimationsSettings.oldSwing && mc.thePlayer.isSwingInProgress && mc.thePlayer.getCurrentEquippedItem() != null && !mc.thePlayer.isBlocking() && !mc.thePlayer.isEating()) {
            GlStateManager.translate(-0.078F, 0.003F, 0.05F);
            GlStateManager.scale(0.85F, 0.85F, 0.85F);
        }

        if (OldAnimationsSettings.oldBowPosition && itemID == 261) {
            GlStateManager.translate(0.0F, 0.05F, 0.04F);
        } else if (OldAnimationsSettings.oldRodPosition && (itemID == 346 || itemID == 398)) {
            GlStateManager.translate(0.08F, -0.027F, -0.33F);
            GlStateManager.scale(0.93F, 1.0F, 1.0F);
        }
    }

    private static float currentHeight = 1.62F;
    private static long lastMillis = System.currentTimeMillis();

    public static float getEyeHeight() {
        final EntityPlayerSP player = mc.thePlayer;
        final int delay = 1000 / 100;
        if (player != null && player.isSneaking()) {
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

        return currentHeight;
    }

    public static boolean oldUpdateEquippedItem(ItemRenderer itemRenderer, EntityPlayer player, ItemStack itemStack) {
        boolean var3 = itemRenderer.equippedItemSlot == player.inventory.currentItem && itemStack == itemRenderer.itemToRender;
        if (itemRenderer.itemToRender == null && itemStack == null) {
            var3 = true;
        }
        if (itemStack != null && itemRenderer.itemToRender != null && itemStack != itemRenderer.itemToRender && itemStack.getItem() == itemRenderer.itemToRender.getItem() && itemStack.getItemDamage() == itemRenderer.itemToRender.getItemDamage()) {
            itemRenderer.itemToRender = itemStack;
            var3 = true;
        }
        return !var3;
    }
}
