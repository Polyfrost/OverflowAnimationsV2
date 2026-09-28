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

import com.google.common.base.MoreObjects
import net.minecraft.client.Minecraft
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState
import net.minecraft.client.renderer.entity.state.AvatarRenderState
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.HumanoidArm
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatureManager
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatures
import java.util.*

fun getHandMultiplier(player: Player): Int {
    //? if <26.3 {
    /*val hand = MoreObjects.firstNonNull(player.swingingArm, InteractionHand.MAIN_HAND)
    *///?} else {
    val hand = MoreObjects.firstNonNull(swingingArm(player), InteractionHand.MAIN_HAND)
    //?}
    val direction = (if (Minecraft.getInstance().options.cameraType.isFirstPerson) 1 else -1)
    return direction * getHandMultiplier(player, hand)
}

fun getHandMultiplier(player: Player, hand: InteractionHand) =
    getArmMultiplier(if (hand == InteractionHand.MAIN_HAND) player.mainArm else player.mainArm.opposite)

fun getHandMultiplier(state: AvatarRenderState, hand: InteractionHand) =
    getArmMultiplier(if (hand == InteractionHand.MAIN_HAND) state.mainArm else state.mainArm.opposite)

fun getArmMultiplier(arm: HumanoidArm) = if (arm == HumanoidArm.RIGHT) 1 else -1

fun Player.getPosWithEyeHeight(tickDelta: Float, eyeHeight: Double) =
    this.getPosition(tickDelta).add(0.0, eyeHeight, 0.0)

fun isBlockingArm(arm: HumanoidArm, armedEntityState: ArmedEntityRenderState) =
    (arm == HumanoidArm.LEFT && armedEntityState.leftArmPose == HumanoidModel.ArmPose.BLOCK) ||
            (arm == HumanoidArm.RIGHT && armedEntityState.rightArmPose == HumanoidModel.ArmPose.BLOCK)

fun applySwingWhilstMining(level: ClientLevel?, player: Player, hitResult: HitResult?) {
    val activeHand = player.usedItemHand
    val hand = if (OverflowAnimationsConfig.instance().extras.offhandUsageSwinging) activeHand else InteractionHand.MAIN_HAND
    if (activeHand == hand) {
        if (hitResult != null && hitResult.type == HitResult.Type.BLOCK) {
            val blockHitResult = hitResult as BlockHitResult
            if (level != null && !level.getBlockState(blockHitResult.blockPos).isAir && !ServerFeatureManager.isPresent(ServerFeatures.MINING_ITEM_USAGE)) {
                //? if <26.3 {
                /*level.addBreakingBlockEffect(blockHitResult.blockPos, blockHitResult.direction)
                *///?} else {
                level.addBreakingBlockEffects(blockHitResult.blockPos, blockHitResult.direction, false)
                //?}
            }
        } else if (!OverflowAnimationsConfig.instance().extras.alwaysUsageSwing) {
            return
        }

        player.fakeHandSwing(hand)
    }
}

/**
 * Can always safely assume that if this returns true, the provided render-state is AvatarRenderState
 *
 * @return Entity id matches the client player id
 */
fun LivingEntityRenderState.isSelf(): Boolean {
    val player = Minecraft.getInstance().player
    return player != null && this is AvatarRenderState && this.id == player.id
}

fun Entity?.isSelf(): Boolean {
    val player = Minecraft.getInstance().player
    return player != null && this != null && this.id == player.id
}

fun Entity.getScale() = if (this is LivingEntity) this.scale else 1.0F