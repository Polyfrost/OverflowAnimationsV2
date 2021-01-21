package club.sk1er.oldanimations;

import club.sk1er.oldanimations.mixins.ItemFoodAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class AnimationHandler {

    private static AnimationHandler INSTANCE = new AnimationHandler();

    public static AnimationHandler getInstance() {
        return INSTANCE;
    }

    public float prevSwingProgress;
    public float swingProgress;
    private int swingProgressInt;
    private boolean isSwingInProgress;

    /**
     * Interpolates swing time using partialTicks. Makes sure to account for possible negative values
     * If there is no swing override, use the default swing
     */
    public float getSwingProgress(float partialTickTime) {
        float f = this.swingProgress - this.prevSwingProgress;

        if (!isSwingInProgress) {
            return Minecraft.getMinecraft().thePlayer.getSwingProgress(partialTickTime);
        }

        if (f < 0.0F) {
            ++f;
        }

        return this.prevSwingProgress + f * partialTickTime;
    }

    /**
     * Gets the number of ticks to play the swing animation for
     */
    private int getArmSwingAnimationEnd(EntityPlayerSP p) {
        return p.isPotionActive(Potion.digSpeed) ?
                5 - p.getActivePotionEffect(Potion.digSpeed).getAmplifier() :
                (p.isPotionActive(Potion.digSlowdown) ?
                        8 + p.getActivePotionEffect(Potion.digSlowdown).getAmplifier() * 2 :
                        6);
    }

    /**
     * Updates the swing progress, also enables swing if hitting a block
     */
    private void updateSwingProgress() {
        EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
        if (p == null) {
            return;
        }
        prevSwingProgress = swingProgress;

        int max = getArmSwingAnimationEnd(p);

        if (Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown() &&
                Minecraft.getMinecraft().objectMouseOver != null &&
                Minecraft.getMinecraft().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if (!this.isSwingInProgress || this.swingProgressInt >= max / 2 || this.swingProgressInt < 0) {
                isSwingInProgress = true;
                swingProgressInt = -1;
            }
        }

        if (this.isSwingInProgress) {
            ++this.swingProgressInt;

            if (this.swingProgressInt >= max) {
                this.swingProgressInt = 0;
                this.isSwingInProgress = false;
            }
        } else {
            this.swingProgressInt = 0;
        }

        this.swingProgress = (float) this.swingProgressInt / (float) max;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            updateSwingProgress();
        }
    }

    /**
     * Renders an item from the first person perspective
     * The following code has been taken from 1.7 and heavily modified to be readable
     * @return whether to cancel default item rendering
     */
    public boolean renderItemInFirstPerson(ItemRenderer renderer, ItemStack stack, float equipProgress, float partialTicks) {
        if(stack == null) {
            //Let the ItemRenderer render the player hand, we don't need to change it at all
            return false;
        }
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        float var4 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
        GL11.glPushMatrix();
        GL11.glRotatef(var4, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();

        float pitch = player.prevRenderArmPitch + (player.renderArmPitch - player.prevRenderArmPitch) * partialTicks;
        float yaw = player.prevRenderArmYaw + (player.renderArmYaw - player.prevRenderArmYaw) * partialTicks;
        GlStateManager.rotate((player.rotationPitch - pitch) * 0.1F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((player.rotationYaw - yaw) * 0.1F, 0.0F, 1.0F, 0.0F);

        GlStateManager.enableRescaleNormal();

        if (stack.getItem() instanceof ItemCloth) {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }

        int i = Minecraft.getMinecraft().theWorld.getCombinedLight(new BlockPos(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ), 0);
        float brightnessX = (float) (i & 65535);
        float brightnessY = (float) (i >> 16);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessX, brightnessY);

        int rgb = stack.getItem().getColorFromItemStack(stack, 0);
        float red = (float) (rgb >> 16 & 255) / 255.0F;
        float green = (float) (rgb >> 8 & 255) / 255.0F;
        float blue = (float) (rgb & 255) / 255.0F;
        GlStateManager.color(red, green, blue, 1);

        GL11.glPushMatrix();

        int useCount = player.getItemInUseCount();
        EnumAction useAction = stack.getItemUseAction();
        float swingProgress = getSwingProgress(partialTicks);

        boolean blockHitOverride = false;
        if (useCount <= 0 && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown()) {
            boolean block = useAction == EnumAction.BLOCK;
            boolean consume = false;
            if(stack.getItem() instanceof ItemFood) {
                boolean alwaysEdible = ((ItemFoodAccessor)(Object)(stack.getItem())).getAlwaysEdible();
                if(Minecraft.getMinecraft().thePlayer.canEat(alwaysEdible)) {
                    consume = useAction == EnumAction.EAT || useAction == EnumAction.DRINK;
                }
            }

            if(block || consume) {
                blockHitOverride = true;
            }
        }

        if((useCount > 0 || blockHitOverride) && useAction != EnumAction.NONE) {
            switch (useAction) {
                case EAT:
                case DRINK:
                    doConsumeAnimation(stack, useCount, partialTicks);
                    doEquipAndSwingTransform(equipProgress, swingProgress);
                    break;
                case BLOCK:
                    doEquipAndSwingTransform(equipProgress, swingProgress);
                    doSwordBlockAnimation();
                    break;
                case BOW:
                    doEquipAndSwingTransform(equipProgress, swingProgress);
                    doBowAnimation(stack, useCount, partialTicks);
            }
        } else {
            doSwingTranslation(swingProgress);
            doEquipAndSwingTransform(equipProgress, swingProgress);
        }

        if (stack.getItem().shouldRotateAroundWhenRendering()) {
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        }

        doFirstPersonTransform(stack);

        if (Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(stack)) {
            renderer.renderItem(player, stack, ItemCameraTransforms.TransformType.FIRST_PERSON);
        } else {
            renderer.renderItem(player, stack, ItemCameraTransforms.TransformType.NONE);
        }

        GL11.glPopMatrix();

        if (stack.getItem() instanceof ItemCloth) {
            GlStateManager.disableBlend();
        }

        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        
        return true;
    }

    /**
     * Transforms the item to make it look like the player is holding it in first person,
     *    replicating the 1.7 positioning
     * (This was a ****** nightmare to put together)
     */
    private void doFirstPersonTransform(ItemStack stack) {
        GlStateManager.translate(0.58800083f, 0.36999986f, -0.77000016f);
        GlStateManager.translate(0, -0.3f, 0.0F);
        GlStateManager.scale(1.5f, 1.5f, 1.5f);
        GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(-0.9375F, -0.0625F, 0.0F);

        GlStateManager.scale(-2, 2, -2);

        if (Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(stack)) {
            GlStateManager.scale(1 / 1.7f, 1 / 1.7f, 1 / 1.7f);
            GlStateManager.rotate(-25, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(0, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(135, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0, -4f * 0.0625F, -2f * 0.0625F);
        }

        GlStateManager.scale(1 / 2f, 1 / 2f, 1 / 2f);
    }

    private void doConsumeAnimation(ItemStack stack, int useCount, float partialTicks) {
        float useAmount = (float) useCount - partialTicks + 1.0F;
        float useAmountNorm = 1.0F - useAmount / (float) stack.getMaxItemUseDuration();
        float useAmountPow = 1.0F - useAmountNorm;
        useAmountPow = useAmountPow * useAmountPow * useAmountPow;
        useAmountPow = useAmountPow * useAmountPow * useAmountPow;
        useAmountPow = useAmountPow * useAmountPow * useAmountPow;
        float useAmountFinal = 1.0F - useAmountPow;
        GlStateManager.translate(0.0F, MathHelper.abs(MathHelper.cos(useAmount / 4.0F *
                (float) Math.PI) * 0.1F) * (float) ((double) useAmountNorm > 0.2D ? 1 : 0), 0.0F);
        GlStateManager.translate(useAmountFinal * 0.6F, -useAmountFinal * 0.5F, 0.0F);
        GlStateManager.rotate(useAmountFinal * 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(useAmountFinal * 10.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(useAmountFinal * 30.0F, 0.0F, 0.0F, 1.0F);
    }

    private void doSwingTranslation(float swingProgress) {
        float swingProgress2 = MathHelper.sin(swingProgress * (float) Math.PI);
        float swingProgress3 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.translate(-swingProgress3 * 0.4F,
                MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI * 2.0F) * 0.2F,
                -swingProgress2 * 0.2F);
    }

    private void doEquipAndSwingTransform(float equipProgress, float swingProgress) {
        GlStateManager.translate(0.56f, -0.52f - (1.0F - equipProgress) * 0.6F, -0.72f);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float swingProgress2 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float swingProgress3 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(-swingProgress2 * 20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-swingProgress3 * 20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-swingProgress3 * 80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }

    private void doSwordBlockAnimation() {
        GlStateManager.translate(-0.5F, 0.2F, 0.0F);
        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
    }

    private void doBowAnimation(ItemStack stack, int useCount, float partialTicks) {
        GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-0.9F, 0.2F, 0.0F);
        float totalPullback = (float) stack.getMaxItemUseDuration() - ((float) useCount - partialTicks + 1.0F);
        float pullbackNorm = totalPullback / 20.0F;
        pullbackNorm = (pullbackNorm * pullbackNorm + pullbackNorm * 2.0F) / 3.0F;

        if (pullbackNorm > 1.0F) {
            pullbackNorm = 1.0F;
        }

        if (pullbackNorm > 0.1F) {
            GlStateManager.translate(0.0F, MathHelper.sin((totalPullback - 0.1F) * 1.3F) * 0.01F
                    * (pullbackNorm - 0.1F), 0.0F);
        }

        GlStateManager.translate(0.0F, 0.0F, pullbackNorm * 0.1F);
        GlStateManager.rotate(-335.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.5F, 0.0F);
        float zScale = 1.0F + pullbackNorm * 0.2F;
        GlStateManager.scale(1.0F, 1.0F, zScale);
        GlStateManager.translate(0.0F, -0.5F, 0.0F);
        GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
    }


}
