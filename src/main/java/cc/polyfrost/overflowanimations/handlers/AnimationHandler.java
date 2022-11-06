package cc.polyfrost.overflowanimations.handlers;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import cc.polyfrost.overflowanimations.mixin.ItemFoodAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class AnimationHandler {

    private static final AnimationHandler INSTANCE = new AnimationHandler();
    private final Minecraft mc = Minecraft.getMinecraft();
    public float prevSwingProgress;
    public float swingProgress;
    private int swingProgressInt;
    private boolean isSwingInProgress;

    public static AnimationHandler getInstance() {
        return INSTANCE;
    }

    /**
     * Interpolates swing time using partialTicks. Makes sure to account for possible negative values
     * If there is no swing override, use the default swing
     */
    public float getSwingProgress(float partialTickTime) {
        float currentProgress = this.swingProgress - this.prevSwingProgress;

        if (!isSwingInProgress) {
            return mc.thePlayer.getSwingProgress(partialTickTime);
        }

        if (currentProgress < 0.0F) {
            ++currentProgress;
        }

        return this.prevSwingProgress + currentProgress * partialTickTime;
    }

    /**
     * Gets the number of ticks to play the swing animation for
     */
    private int getArmSwingAnimationEnd(EntityPlayerSP player) {
        return player.isPotionActive(Potion.digSpeed) ? 5 - player.getActivePotionEffect(Potion.digSpeed).getAmplifier() :
                (player.isPotionActive(Potion.digSlowdown) ? 8 + player.getActivePotionEffect(Potion.digSlowdown).getAmplifier() * 2 : 6);
    }

    /**
     * Updates the swing progress, also enables swing if hitting a block
     */
    private void updateSwingProgress() {
        final EntityPlayerSP player = mc.thePlayer;
        if (player == null) {
            return;
        }

        prevSwingProgress = swingProgress;

        int max = getArmSwingAnimationEnd(player);
        if (this.swingProgressInt >= max || !mc.gameSettings.keyBindUseItem.isKeyDown()) {
            this.swingProgressInt = 0;
            this.isSwingInProgress = false;
        }

        if (OldAnimationsSettings.punching && mc.gameSettings.keyBindAttack.isKeyDown() &&
                mc.gameSettings.keyBindUseItem.isKeyDown() &&
                mc.objectMouseOver != null &&
                mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if (!this.isSwingInProgress || this.swingProgressInt >= max >> 1 || this.swingProgressInt < 0) {
                isSwingInProgress = true;
                swingProgressInt = -1;
            }
        }

        if (this.isSwingInProgress) {
            ++this.swingProgressInt;
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
     * Renders an item from the first-person perspective
     * The following code has been taken from 1.7 and heavily modified to be readable
     *
     * @return whether to cancel default item rendering
     */
    @SuppressWarnings("deprecation")
    public boolean renderItemInFirstPerson(ItemRenderer renderer, ItemStack stack, float equipProgress, float partialTicks) {
        if (stack == null) {
            //Let the ItemRenderer render the player hand, we don't need to change it at all
            return false;
        }

        final Item item = stack.getItem();
        if (item == Items.filled_map || mc.getRenderItem().shouldRenderItemIn3D(stack)) {
            return false;
        }

        final EnumAction action = stack.getItemUseAction();
        if ((item == Items.fishing_rod && !OldAnimationsSettings.oldRod)
                || (action == EnumAction.NONE && !OldAnimationsSettings.oldModel)
                || (action == EnumAction.BLOCK && !OldAnimationsSettings.oldSwordBlock)
                || (action == EnumAction.BOW && !OldAnimationsSettings.oldBow)) {
            return false;
        }

        final EntityPlayerSP player = mc.thePlayer;

        float var4 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.rotate(var4, 1, 0, 0);
        GlStateManager.rotate(player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();

        float pitch = player.prevRenderArmPitch + (player.renderArmPitch - player.prevRenderArmPitch) * partialTicks;
        float yaw = player.prevRenderArmYaw + (player.renderArmYaw - player.prevRenderArmYaw) * partialTicks;
        GlStateManager.rotate((player.rotationPitch - pitch) * 0.1F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((player.rotationYaw - yaw) * 0.1F, 0.0F, 1.0F, 0.0F);

        GlStateManager.enableRescaleNormal();

        if (item instanceof ItemCloth) {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        }

        int i = mc.theWorld.getCombinedLight(new BlockPos(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ), 0);
        float brightnessX = (float) (i & 65535);
        float brightnessY = (float) (i >> 16);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessX, brightnessY);

        int rgb = item.getColorFromItemStack(stack, 0);
        float red = (float) (rgb >> 16 & 255) / 255.0F;
        float green = (float) (rgb >> 8 & 255) / 255.0F;
        float blue = (float) (rgb & 255) / 255.0F;
        GlStateManager.color(red, green, blue, 1);

        GlStateManager.pushMatrix();

        final int useCount = player.getItemInUseCount();
        final float swingProgress = getSwingProgress(partialTicks);

        boolean blockHitOverride = false;
        if (OldAnimationsSettings.punching && useCount <= 0 && mc.gameSettings.keyBindUseItem.isKeyDown()) {
            boolean block = action == EnumAction.BLOCK;
            boolean consume = false;
            if (item instanceof ItemFood) {
                boolean alwaysEdible = ((ItemFoodAccessor) item).getAlwaysEdible();
                if (player.canEat(alwaysEdible)) {
                    consume = action == EnumAction.EAT || action == EnumAction.DRINK;
                }
            }

            if (block || consume) {
                blockHitOverride = true;
            }
        }

        if ((useCount > 0 || blockHitOverride) && action != EnumAction.NONE && mc.thePlayer.isUsingItem()) {
            switch (action) {
                case EAT:
                case DRINK:
                    doConsumeAnimation(stack, useCount, partialTicks);
                    doEquipAndSwingTransform(equipProgress, OldAnimationsSettings.oldBlockhitting ? swingProgress : 0);
                    break;
                case BLOCK:
                    doEquipAndSwingTransform(equipProgress, OldAnimationsSettings.oldBlockhitting ? swingProgress : 0);
                    doSwordBlockAnimation();
                    break;
                case BOW:
                    doEquipAndSwingTransform(equipProgress, OldAnimationsSettings.oldBlockhitting ? swingProgress : 0);
                    doBowAnimation(stack, useCount, partialTicks);
            }
        } else {
            doSwingTranslation(swingProgress);
            doEquipAndSwingTransform(equipProgress, swingProgress);
        }

        if (item.shouldRotateAroundWhenRendering()) {
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        }

        OverflowAnimations.renderingStack = stack;
        if (doFirstPersonTransform(stack)) {
            renderer.renderItem(player, stack, ItemCameraTransforms.TransformType.FIRST_PERSON);
        } else {
            renderer.renderItem(player, stack, ItemCameraTransforms.TransformType.NONE);
        }

        GlStateManager.popMatrix();

        if (item instanceof ItemCloth) {
            GlStateManager.disableBlend();
        }

        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();

        return true;
    }

    public void doSwordBlock3rdPersonTransform() {
        if (OldAnimationsSettings.oldSwordBlock3) {
            GlStateManager.translate(-0.15f, -0.2f, 0);
            GlStateManager.rotate(70, 1, 0, 0);
            GlStateManager.translate(0.119f, 0.2f, -0.024f);
        }
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (OldAnimationsSettings.mixcesAnimations) {
            GlStateManager.rotate(20, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(10, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(0, 0.0f, 0.0f, 1.0f);
            GlStateManager.translate(0.072, 0.035, 0.0);
            if (player.isSneaking()) {
                GlStateManager.translate(-0.033, -0.0035, 0.05);
            }
        }
    }

    /**
     * Transforms the item to make it look like the player is holding it in first person,
     * replicating the 1.7 positioning
     * (This was a ****** nightmare to put together)
     *
     * @return Whether to perform the 1.8 First Person transform as well
     */
    private boolean doFirstPersonTransform(ItemStack stack) {
        switch (stack.getItemUseAction()) {
            case BOW:
                if (!OldAnimationsSettings.oldBow) return true;
                break;
            case EAT:
            case DRINK:
                if (!OldAnimationsSettings.oldEating) return true;
                break;
            case BLOCK:
                if (!OldAnimationsSettings.oldSwordBlock) return true;
                break;
            case NONE:
                if (!OldAnimationsSettings.oldModel) return true;
        }

        GlStateManager.translate(0.58800083f, 0.36999986f, -0.77000016f);
        GlStateManager.translate(0, -0.3f, 0.0F);
        GlStateManager.scale(1.5f, 1.5f, 1.5f);
        GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(-0.9375F, -0.0625F, 0.0F);

        GlStateManager.scale(-2, 2, -2);

        if (mc.getRenderItem().shouldRenderItemIn3D(stack)) {
            GlStateManager.scale(0.58823526f, 0.58823526f, 0.58823526f);
            GlStateManager.rotate(-25, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(0, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(135, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0, -0.25f, -0.125f);
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            return true;
        }

        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        return false;
    }

    private void doConsumeAnimation(ItemStack stack, int useCount, float partialTicks) {
        if (OldAnimationsSettings.oldEating) {
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
        } else {
            float f = (float) useCount - partialTicks + 1.0F;
            float f1 = f / (float) stack.getMaxItemUseDuration();
            float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * (float) Math.PI) * 0.1F);

            if (f1 >= 0.8F) {
                f2 = 0.0F;
            }

            GlStateManager.translate(0.0F, f2, 0.0F);
            float f3 = 1.0F - (float) Math.pow(f1, 27.0D);
            GlStateManager.translate(f3 * 0.6F, f3 * -0.5F, f3 * 0.0F);
            GlStateManager.rotate(f3 * 90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f3 * 30.0F, 0.0F, 0.0F, 1.0F);
        }
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

        if (OldAnimationsSettings.oldBow) {
            GlStateManager.rotate(-335.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.5F, 0.0F);
        }

        float zScale = 1.0F + pullbackNorm * 0.2F;
        GlStateManager.scale(1.0F, 1.0F, zScale);

        if (OldAnimationsSettings.oldBow) {
            GlStateManager.translate(0.0F, -0.5F, 0.0F);
            GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
        }
    }
}
