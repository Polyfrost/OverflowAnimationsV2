package org.polyfrost.overflowanimations.config

import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Checkbox
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.elements.SubConfig
import net.minecraft.client.Minecraft

class ItemPositionSettings : SubConfig("Item Customization Settings", "itemcustomization.json", null, true) {

    // Swing Position Customization
    @Slider(
        name = "Item Swing X Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Item Swing Position",
        instant = true
    )
    var itemSwingPositionX: Float = 0.0f

    @Slider(
        name = "Item Swing Y Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Item Swing Position",
        instant = true
    )
    var itemSwingPositionY: Float = 0.0f

    @Slider(
        name = "Item Swing Z Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Item Swing Position",
        instant = true
    )
    var itemSwingPositionZ: Float = 0.0f

    @Button(
        name = "Reset Item Swing Transformations",
        text = "Reset",
        category = "Customize Item Positions",
        subcategory = "Item Swing Position"
    )
    var resetSwing: Runnable = (Runnable {
        itemSwingPositionX = 0.0f
        itemSwingPositionY = 0.0f
        itemSwingPositionZ = 0.0f
    })

    // Eating/Drinking Position
    @Slider(
        name = "Eating/Drinking X Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Eating/Drinking Position",
        instant = true
    )
    var consumePositionX: Float = 0.0f

    @Slider(
        name = "Eating/Drinking Y Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Eating/Drinking Position",
        instant = true
    )
    var consumePositionY: Float = 0.0f

    @Slider(
        name = "Eating/Drinking Z Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Eating/Drinking Position",
        instant = true
    )
    var consumePositionZ: Float = 0.0f

    @Slider(
        name = "Eating/Drinking Rotation Yaw",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Eating/Drinking Position",
        instant = true
    )
    var consumeRotationYaw: Float = 0.0f

    @Slider(
        name = "Eating/Drinking Rotation Pitch",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Eating/Drinking Position",
        instant = true
    )
    var consumeRotationPitch: Float = 0.0f

    @Slider(
        name = "Eating/Drinking Rotation Roll",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Eating/Drinking Position",
        instant = true
    )
    var consumeRotationRoll: Float = 0.0f

    @Slider(
        name = "Eating/Drinking Scale",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Eating/Drinking Position",
        instant = true
    )
    var consumeScale: Float = 0.0f

    @Slider(
        name = "Eating/Drinking Intensity Animation",
        min = -6.5f,
        max = 6.5f,
        category = "Customize Item Positions",
        subcategory = "Eating/Drinking Position",
        instant = true
    )
    var consumeIntensity: Float = 0.0f

    @Slider(
        name = "Eating/Drinking Rotation Speed",
        min = -1.0f,
        max = 1.0f,
        category = "Customize Item Positions",
        subcategory = "Eating/Drinking Position",
        instant = true
    )
    var consumeSpeed: Float = 0.0f

    @Checkbox(
        name = "Scale Eating/Drinking Based on Item Position",
        description = "Scales the Eating/Drinking animation based on the position of the item.",
        category = "Customize Item Positions",
        subcategory = "Eating/Drinking Position"
    )
    var shouldScaleEat: Boolean = false

    @Button(
        name = "Reset Eating/Drinking Transformations",
        text = "Reset",
        category = "Customize Item Positions",
        subcategory = "Eating/Drinking Position"
    )
    var resetConsume: Runnable = (Runnable {
        consumePositionX = 0.0f
        consumePositionY = 0.0f
        consumePositionZ = 0.0f
        consumeRotationYaw = 0.0f
        consumeRotationPitch = 0.0f
        consumeRotationRoll = 0.0f
        consumeScale = 0.0f
        consumeIntensity = 0.0f
        consumeSpeed = 0.0f
        shouldScaleEat = false
    })

    // Sword Block Position
    @Slider(
        name = "Sword Block X Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Sword Block Position",
        instant = true
    )
    var blockedPositionX: Float = 0.0f

    @Slider(
        name = "Sword Block Y Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Sword Block Position",
        instant = true
    )
    var blockedPositionY: Float = 0.0f

    @Slider(
        name = "Sword Block Z Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Sword Block Position",
        instant = true
    )
    var blockedPositionZ: Float = 0.0f

    @Slider(
        name = "Sword Block Rotation Yaw",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Sword Block Position",
        instant = true
    )
    var blockedRotationYaw: Float = 0.0f

    @Slider(
        name = "Sword Block Rotation Pitch",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Sword Block Position",
        instant = true
    )
    var blockedRotationPitch: Float = 0.0f

    @Slider(
        name = "Sword Block Rotation Roll",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Sword Block Position",
        instant = true
    )
    var blockedRotationRoll: Float = 0.0f

    @Slider(
        name = "Sword Block Scale",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Sword Block Position",
        instant = true
    )
    var blockedScale: Float = 0.0f

    @Button(
        name = "Reset Sword Block Transformations",
        text = "Reset",
        category = "Customize Item Positions",
        subcategory = "Sword Block Position"
    )
    var resetBlockItem: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        blockedPositionX = 0.0f
        blockedPositionY = 0.0f
        blockedPositionZ = 0.0f
        blockedRotationYaw = 0.0f
        blockedRotationPitch = 0.0f
        blockedRotationRoll = 0.0f
        blockedScale = 0.0f
    })

    // Dropped Item Position
    @Slider(
        name = "Dropped Item X Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Dropped Item Position",
        instant = true
    )
    var droppedPositionX: Float = 0.0f

    @Slider(
        name = "Dropped Item Y Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Dropped Item Position",
        instant = true
    )
    var droppedPositionY: Float = 0.0f

    @Slider(
        name = "Dropped Item Z Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Dropped Item Position",
        instant = true
    )
    var droppedPositionZ: Float = 0.0f

    @Slider(
        name = "Dropped Item Rotation Yaw",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Dropped Item Position",
        instant = true
    )
    var droppedRotationYaw: Float = 0.0f

    @Slider(
        name = "Dropped Item Rotation Pitch",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Dropped Item Position",
        instant = true
    )
    var droppedRotationPitch: Float = 0.0f

    @Slider(
        name = "Dropped Item Rotation Roll",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Dropped Item Position",
        instant = true
    )
    var droppedRotationRoll: Float = 0.0f

    @Slider(
        name = "Dropped Item Scale",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Dropped Item Position",
        instant = true
    )
    var droppedScale: Float = 0.0f

    @Button(
        name = "Reset Dropped Item Transformations",
        text = "Reset",
        category = "Customize Item Positions",
        subcategory = "Dropped Item Position"
    )
    var resetDropped: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        droppedPositionX = 0.0f
        droppedPositionY = 0.0f
        droppedPositionZ = 0.0f
        droppedRotationYaw = 0.0f
        droppedRotationPitch = 0.0f
        droppedRotationRoll = 0.0f
        droppedScale = 0.0f
    })

    // Projectiles Position
    @Slider(
        name = "Thrown Projectile X Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Thrown Projectile Position",
        instant = true
    )
    var projectilePositionX: Float = 0.0f

    @Slider(
        name = "Thrown Projectile Y Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Thrown Projectile Position",
        instant = true
    )
    var projectilePositionY: Float = 0.0f

    @Slider(
        name = "Thrown Projectile Z Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Thrown Projectile Position",
        instant = true
    )
    var projectilePositionZ: Float = 0.0f

    @Slider(
        name = "Thrown Projectile Rotation Yaw",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Thrown Projectile Position",
        instant = true
    )
    var projectileRotationYaw: Float = 0.0f

    @Slider(
        name = "Thrown Projectile Rotation Pitch",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Thrown Projectile Position",
        instant = true
    )
    var projectileRotationPitch: Float = 0.0f

    @Slider(
        name = "Thrown Projectile Rotation Roll",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Thrown Projectile Position",
        instant = true
    )
    var projectileRotationRoll: Float = 0.0f

    @Slider(
        name = "Thrown Projectile Scale",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Thrown Projectile Position",
        instant = true
    )
    var projectileScale: Float = 0.0f

    @Button(
        name = "Reset Thrown Projectile Transformations",
        text = "Reset",
        category = "Customize Item Positions",
        subcategory = "Thrown Projectile Position"
    )
    var resetProjectile: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        projectilePositionX = 0.0f
        projectilePositionY = 0.0f
        projectilePositionZ = 0.0f
        projectileRotationYaw = 0.0f
        projectileRotationPitch = 0.0f
        projectileRotationRoll = 0.0f
        projectileScale = 0.0f
    })

    // Fireball Position
    @Slider(
        name = "Fireball Projectile X Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Fireball Projectile Position",
        instant = true
    )
    var fireballPositionX: Float = 0.0f

    @Slider(
        name = "Fireball Projectile Y Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Fireball Projectile Position",
        instant = true
    )
    var fireballPositionY: Float = 0.0f

    @Slider(
        name = "Fireball Projectile Z Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Fireball Projectile Position",
        instant = true
    )
    var fireballPositionZ: Float = 0.0f

    @Slider(
        name = "Fireball Projectile Rotation Yaw",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Fireball Projectile Position",
        instant = true
    )
    var fireballRotationYaw: Float = 0.0f

    @Slider(
        name = "Fireball Projectile Rotation Pitch",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Fireball Projectile Position",
        instant = true
    )
    var fireballRotationPitch: Float = 0.0f

    @Slider(
        name = "Fireball Projectile Rotation Roll",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Fireball Projectile Position",
        instant = true
    )
    var fireballRotationRoll: Float = 0.0f

    @Slider(
        name = "Fireball Projectile Scale",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Fireball Projectile Position",
        instant = true
    )
    var fireballScale: Float = 0.0f

    @Button(
        name = "Reset Fireball Projectile Transformations",
        text = "Reset",
        category = "Customize Item Positions",
        subcategory = "Fireball Projectile Position"
    )
    var resetFireball: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        fireballPositionX = 0.0f
        fireballPositionY = 0.0f
        fireballPositionZ = 0.0f
        fireballRotationYaw = 0.0f
        fireballRotationPitch = 0.0f
        fireballRotationRoll = 0.0f
        fireballScale = 0.0f
    })

    // Fishing Line Position
    @Switch(
        name = "Custom Fishing Rod Line Position",
        description = "Allows customization of the fishing rod line.",
        category = "Customize Item Positions",
        subcategory = "Fishing Rod Line Position"
    )
    var customRodLine: Boolean = false

    @Slider(
        name = "Fishing Line X Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Fishing Rod Line Position",
        instant = true
    )
    var fishingLinePositionX: Float = -0.36f

    @Slider(
        name = "Fishing Line Y Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Fishing Rod Line Position",
        instant = true
    )
    var fishingLinePositionY: Float = 0.03f

    @Slider(
        name = "Fishing Line Z Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Fishing Rod Line Position",
        instant = true
    )
    var fishingLinePositionZ: Float = 0.35f

    @Button(
        name = "Reset Fishing Rod Line Transformations",
        text = "Reset",
        category = "Customize Item Positions",
        subcategory = "Fishing Rod Line Position"
    )
    var resetFishingLine: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        fishingLinePositionX = if (MainModSettings.oldSettings.fishingRodPosition) -0.5f else -0.36f
        fishingLinePositionY = 0.03f
        fishingLinePositionZ = if (MainModSettings.oldSettings.fishingRodPosition) 0.8f else 0.35f
    })

    init {
        initialize()
    }

}
