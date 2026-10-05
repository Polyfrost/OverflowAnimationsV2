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

import net.minecraft.client.player.LocalPlayer
//? if >=26.3 {
import net.minecraft.client.renderer.entity.state.HumanoidRenderState
//?}
//? if <26.3 {
/*import net.minecraft.network.protocol.game.ClientboundAnimatePacket
import net.minecraft.network.protocol.game.ServerboundSwingPacket
*///?} else {
import net.minecraft.network.protocol.game.ClientboundSwingAnimationPacket
//?}
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.EntityTypeTags
import net.minecraft.world.InteractionHand
import net.minecraft.world.effect.MobEffectUtil
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
//? if >=26.3 {
import net.minecraft.world.item.component.SwingAnimation
//?}
import net.minecraft.world.item.enchantment.Enchantments
import org.polyfrost.overflowanimations.OverflowAnimations
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig
import org.polyfrost.overflowanimations.handler.compatibility.HAS_OVERFLOW_PARTICLES
import org.polyfrost.overflowanimations.handler.compatibility.OverflowParticlesCompat
import org.polyfrost.overflowanimations.mixins.accessor.LivingEntityAccessor
//? if >=26.3 {
import org.polyfrost.overflowanimations.mixins.accessor.LivingEntity_SwingStateAccessor
import org.polyfrost.overflowanimations.util.duck.SwingStateExt
//?}
//? if >=1.21.11 <26.3 {
/*import org.polyfrost.overflowanimations.util.duck.SwingLockExt
*///?}
import java.util.*
import kotlin.math.exp
import kotlin.math.max

//? if <26.3 {
/*// Fake Swinging, Doesn't Send A Packet
fun Player.fakeHandSwing(hand: InteractionHand) {
    if (this.isNotSwinging()) {
        this.swingTime = -1
        this.swinging = true
        this.swingingArm = hand
        //? if >=1.21.11 {
        (this as SwingLockExt).`overflowanimations$lockSwing`(hand)
        //?}
    }
}

fun Player.isNotSwinging() =
    !this.swinging || this.swingTime >= (this as LivingEntityAccessor).`overflowanimations$getSwingDuration`() / 2 || this.swingTime < 0

// Sends necessary swing packets, without playing the player hand swing animation
fun LocalPlayer.sendSwingPacket(hand: InteractionHand) {
    val level = this.level()
    if (this.isNotSwinging() && level is ServerLevel) {
        val swingHand =
            if (hand == InteractionHand.MAIN_HAND) ClientboundAnimatePacket.SWING_MAIN_HAND else ClientboundAnimatePacket.SWING_OFF_HAND
        //? if <1.21.9 {
        /*level.chunkSource.broadcast(this, ClientboundAnimatePacket(this, swingHand))
        *///?} else {
        level.chunkSource.sendToTrackingPlayers(this, ClientboundAnimatePacket(this, swingHand))
        //?}
    }

    this.connection.send(ServerboundSwingPacket(hand))
}
*///?} else {
fun attackArm(state: HumanoidRenderState) = state.useItemHand.asArm(state.mainArm)

fun swingState(livingEntity: LivingEntity) = (livingEntity as LivingEntityAccessor).`overflowanimations$getSwingState`()

fun activeSwing(livingEntity: LivingEntity) = (swingState(livingEntity) as LivingEntity_SwingStateAccessor).`overflowanimations$getCurrentSwing`()

fun swingingArm(livingEntity: LivingEntity) = activeSwing(livingEntity)?.hand

// Fake Swinging, Doesn't Send A Packet
fun Player.fakeHandSwing(hand: InteractionHand) {
    val animation = SwingAnimation.DEFAULT
    (swingState(this) as SwingStateExt).`overflowanimations$forceSwing`(
        hand,
        animation,
        (this as LivingEntityAccessor).`overflowanimations$getModifiedSwingDuration`(animation)
    )
}

// Sends necessary swing packets, without playing the player hand swing animation
fun LocalPlayer.sendSwingPacket(hand: InteractionHand, animation: SwingAnimation): Boolean {
    return if ((swingState(this) as SwingStateExt).`overflowanimations$canStartSwing`()) {
        val level = this.level()
        if (level is ServerLevel) {
            level.chunkSource.sendToTrackingPlayers(this, ClientboundSwingAnimationPacket(this, hand, animation))
        }

        true
    } else {
        false
    }
}
//?}

/**
 * Code sourced from Animatium Legacy & Modified for Modern Use
 */
//? if <26.3 {
/*fun LivingEntity.getItemSwingSpeed(fallback: Int): Int {
*///?} else {
fun LivingEntity.getItemSwingSpeed(animation: SwingAnimation, fallback: Int): Int {
//?}
    val items = OverflowAnimationsConfig.instance().items
    if (OverflowAnimations.isEnabled() && items.customSwingSpeed) {
        //? if <1.21.11 {
        /*val swingDuration = 6 // Items have no swing animation component yet
        *///?} elif <26.3 {
        /*val swingingHand = if (this.swingingArm != null) this.swingingArm!! else InteractionHand.MAIN_HAND
        val stack = this.getItemInHand(swingingHand)
        val swingDuration = (this as SwingLockExt).`overflowanimations$swingAnimation`(stack.swingAnimation).duration()
        *///?}

        //? if >=26.3 {
        val swingDuration = animation.duration()
        //?}
        val itemSwingSpeed = items.itemSwingSpeed
        val hasteSwingSpeed = items.hasteSwingSpeed
        val miningFatigueSwingSpeed = items.miningFatigueSwingSpeed
        if (!(itemSwingSpeed == 0.0F && hasteSwingSpeed == 0.0F && miningFatigueSwingSpeed == 0.0F)) {
            if (MobEffectUtil.hasDigSpeed(this) && !items.ignoreHasteSpeed) {
                val durationOffset =
                    swingDuration - (1 + MobEffectUtil.getDigSpeedAmplification(this))
                return max((durationOffset * exp(-hasteSwingSpeed)).toInt(), 1)
            } else if (this.hasEffect(MobEffects.MINING_FATIGUE) && !items.ignoreMiningFatigueSpeed) {
                val durationOffset =
                    swingDuration + (1 + Objects.requireNonNull(this.getEffect(MobEffects.MINING_FATIGUE))!!.amplifier) * 2
                return max((durationOffset * exp(-miningFatigueSwingSpeed)).toInt(), 1)
            } else {
                return max((swingDuration * exp(-itemSwingSpeed)).toInt(), 1)
            }
        }
    }

    return fallback
}

private val CONDITIONAL_DAMAGE_ENCHANTMENTS = mapOf(
    Enchantments.SMITE to EntityTypeTags.SENSITIVE_TO_SMITE,
    Enchantments.BANE_OF_ARTHROPODS to EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS,
    Enchantments.IMPALING to EntityTypeTags.SENSITIVE_TO_IMPALING,
)

fun LocalPlayer.fakeAttackParticles(target: Entity) {
    if (this.isSpectator || !target.isAttackable) {
        return
    }
    if (this.fallDistance > 0.0F && !this.onGround() && !this.onClimbable() && !this.isInWater && !this.hasEffect(MobEffects.BLINDNESS) && !this.isPassenger && target is LivingEntity) {
        this.crit(target)
    }
    val type = target.type.builtInRegistryHolder()
    if (this.mainHandItem.enchantments.keySet().any { enchantment -> enchantment.`is`(Enchantments.SHARPNESS) || CONDITIONAL_DAMAGE_ENCHANTMENTS.any { (key, tag) -> enchantment.`is`(key) && type.`is`(tag) } }) {
        this.magicCrit(target)
    }
    if (HAS_OVERFLOW_PARTICLES) {
        OverflowParticlesCompat.postAttack(this, target)
    }
}
