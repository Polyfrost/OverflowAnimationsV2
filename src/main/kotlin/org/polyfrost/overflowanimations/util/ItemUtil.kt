/**
 * OverflowAnimations
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2027 lowercasebtw
 * Copyright (C) 2024-2027 mixces
 * Copyright (C) 2024-2027 Contributors to the project retain their copyright
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * "MINECRAFT" LINKING EXCEPTION TO THE GPL
 */

package org.polyfrost.overflowanimations.util

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState
//? if <1.21.9 {
/*import net.minecraft.client.renderer.entity.state.PlayerRenderState
*///?} else {
import net.minecraft.client.renderer.entity.state.AvatarRenderState
//?}
import net.minecraft.resources.Identifier
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.*
import net.minecraft.world.level.block.*
import org.polyfrost.overflowanimations.OverflowAnimations
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig
import org.polyfrost.overflowanimations.util.enums.FishingRodVersionSetting
import java.lang.Double.isNaN
import kotlin.math.roundToInt

fun isSwordItem(stack: ItemStack) = stack.`is`(ItemTags.SWORDS)

fun isAxeItem(stack: ItemStack) = stack.`is`(ItemTags.AXES)

fun isPickaxeItem(stack: ItemStack) = stack.`is`(ItemTags.PICKAXES)

fun isShovelItem(stack: ItemStack) = stack.`is`(ItemTags.SHOVELS)

fun isHoeItem(stack: ItemStack) = stack.`is`(ItemTags.HOES)

fun isDiggerItem(stack: ItemStack) = isAxeItem(stack) || isPickaxeItem(stack) || isShovelItem(stack) || isHoeItem(stack)

fun isShieldItem(stack: ItemStack) = stack.item is ShieldItem || stack.`is`(Items.SHIELD)

fun isFishingRodItem(stack: ItemStack) = stack.item is FishingRodItem || stack.item is FoodOnAStickItem<*>

fun isRangedWeaponItem(stack: ItemStack) = stack.item is ProjectileWeaponItem

fun isHandheldItem(stack: ItemStack) =
    isDiggerItem(stack) ||
            isSwordItem(stack) ||
            isFishingRodItem(stack) ||
            listOf(Items.MACE, Items.TRIDENT, Items.STICK, Items.BREEZE_ROD, Items.BLAZE_ROD).contains(stack.item)

fun isThinBlockItem(stack: ItemStack): Boolean {
    val block = Block.byItem(stack.item)
    return block is CarpetBlock ||
            block is TrapDoorBlock ||
            block is PressurePlateBlock ||
            block is SnowLayerBlock ||
            block is DaylightDetectorBlock
}

fun isSkullBlock(stack: ItemStack) = Block.byItem(stack.item) is SkullBlock

fun isBlockItemBlacklisted(stack: ItemStack): Boolean {
    val block = Block.byItem(stack.item)
    return block is BannerBlock ||
            block is RodBlock ||
            block is BedBlock ||
            (isSkullBlock(stack) && !OverflowAnimationsConfig.instance().items.mobHeadIcons)
}

fun isItemBlacklisted(stack: ItemStack) =
    isShieldItem(stack) ||
            isBlockItemBlacklisted(stack) ||
            //? if <1.21.11 {
            /*stack.`is`(Items.CROSSBOW)
            *///?} else {
            stack.`is`(Items.CROSSBOW) ||
            stack.`is`(ItemTags.SPEARS)
            //?}

fun isItemBlacklistedInThirdPerson(stack: ItemStack) =
    isItemBlacklisted(stack) || stack.`is`(Items.SPYGLASS)

fun isSwingItemBlacklisted(stack: ItemStack) =
    stack.item is ProjectileItem ||
            stack.item is BucketItem ||
            stack.item is ShearsItem ||
            stack.item is EnderpearlItem

fun isBlock3d(stack: ItemStack, usesBlockLight: Boolean) = stack.item is BlockItem && usesBlockLight

fun applyLegacyFirstPersonTransforms(poseStack: PoseStack, direction: Int, runnable: Runnable) {
    //? if <26.3 {
    /*poseStack.mulPose(Axis.YP.rotationDegrees(direction * 45.0F))
    *///?} else {
    poseStack.rotate(Axis.YP.rotationDegrees(direction * 45.0F))
    //?}
    poseStack.scale(0.4F, 0.4F, 0.4F)
    runnable.run()
    poseStack.scale(1 / 0.4F, 1 / 0.4F, 1 / 0.4F)
    //? if <26.3 {
    /*poseStack.mulPose(Axis.YP.rotationDegrees(direction * -45.0F))
    *///?} else {
    poseStack.rotate(Axis.YP.rotationDegrees(direction * -45.0F))
    //?}
}

fun shouldApplyItemPositionsInThirdPerson(armedEntityRenderState: ArmedEntityRenderState, stack: ItemStack, useBlockLight: Boolean) =
    if (OverflowAnimationsConfig.instance().items.itemPositionsInThirdPerson &&
        //? if <1.21.9 {
        /*(OverflowAnimationsConfig.instance().items.entityItemPositions || armedEntityRenderState is PlayerRenderState)
        *///?} else {
        (OverflowAnimationsConfig.instance().items.entityItemPositions || armedEntityRenderState is AvatarRenderState)
        //?}
    ) {
        hasLegacyThirdPersonTransform(stack, useBlockLight)
    } else if (OverflowAnimationsConfig.instance().items.fishingRodVersion == FishingRodVersionSetting.V1_7) {
        isFishingRodItem(armedEntityRenderState.`overflowanimations$getItemHeldByArm`(armedEntityRenderState.mainArm))
    } else {
        OverflowAnimationsConfig.instance().other.thirdPersonSwordBlockingPosition && isBlockingArm(
            armedEntityRenderState.mainArm,
            armedEntityRenderState
        )
    }

fun hasLegacyThirdPersonTransform(stack: ItemStack, usesBlockLight: Boolean) =
    !OverflowAnimationsConfig.instance().items.strictItemPositionsInThirdPerson ||
            isBlock3d(stack, usesBlockLight) ||
            stack.`is`(Items.BOW) ||
            isHandheldItem(stack)

fun getLegacyDurabilityColorValue(stack: ItemStack): Int {
    val value = (255.0 - stack.damageValue.toDouble() * 255.0 / stack.maxDamage.toDouble())
    return if (!isNaN(value)) {
        value.roundToInt()
    } else {
        0
    }
}

fun getLegacyItemRarity(stack: ItemStack) =
    if (listOf(Items.GOLDEN_APPLE, Items.END_CRYSTAL).contains(stack.item)) {
        Rarity.RARE
    } else if (listOf(Items.NETHER_STAR, Items.ELYTRA, Items.DRAGON_HEAD).contains(stack.item)) {
        Rarity.UNCOMMON
    } else if (stack.item == Items.ENCHANTED_GOLDEN_APPLE) {
        Rarity.EPIC
    } else if (stack.item == Items.TRIDENT) {
        Rarity.COMMON
    } else {
        stack.rarity
    }

fun getMobHeadLocation(item: Item): Identifier? {
    val block = Block.byItem(item)
    return if (block == Blocks.AIR || !(block is SkullBlock && block.type is SkullBlock.Types)) {
        null
    } else {
        when (block.type) {
            SkullBlock.Types.SKELETON -> OverflowAnimations.location("skeleton_skull")
            SkullBlock.Types.WITHER_SKELETON -> OverflowAnimations.location("wither_skeleton_skull")
            SkullBlock.Types.PLAYER -> OverflowAnimations.location("player_skull")
            SkullBlock.Types.ZOMBIE -> OverflowAnimations.location("zombie_skull")
            SkullBlock.Types.CREEPER -> OverflowAnimations.location("creeper_skull")
            SkullBlock.Types.PIGLIN -> OverflowAnimations.location("piglin_skull")
            SkullBlock.Types.DRAGON -> OverflowAnimations.location("dragon_skull")
            else -> null
        }
    }
}

// TODO/NOTE: Might need rework? as vanilla now has the fix as of 1.21.11+ but doesn't seem fully the same/accurate
fun shouldInstantlyReplaceVisibleItem1_8(prevStack: ItemStack, currentStack: ItemStack): Boolean {
    // TODO/NOTE: Apparently 1.7 doesn't do any special checks inside the inventory
    val itemsMatch = ItemStack.isSameItem(prevStack, currentStack)
    val durabilityMatch = prevStack.damageValue == currentStack.damageValue
    val countMatch = prevStack.count == currentStack.count
    return (itemsMatch && (!durabilityMatch || !countMatch))
}