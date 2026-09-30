plugins {
    id("dev.kikugie.stonecutter")
    id("com.diffplug.spotless") version "8.2.1"
}

stonecutter active "26.4" /* [SC] DO NOT EDIT */

stonecutter tasks {
    order("publishMods")
}

stonecutter parameters {
    replacements {
        // 1.21.11 renamed ResourceLocation to Identifier and moved many classes into subpackages
        string(current.parsed < "1.21.11" && current.parsed > "1.8.9") {
            replace("Identifier", "ResourceLocation")
            replace("net.minecraft.client.renderer.rendertype.RenderType;", "net.minecraft.client.renderer.RenderType;")
            replace("net/minecraft/client/renderer/rendertype/RenderType;", "net/minecraft/client/renderer/RenderType;")
            listOf(
                "client.model.object.equipment.ElytraModel" to "client.model.ElytraModel",
                "client.model.object.skull.SkullModelBase" to "client.model.SkullModelBase",
                "client.model.player.PlayerCapeModel" to "client.model.PlayerCapeModel",
                "client.model.player.PlayerModel" to "client.model.PlayerModel",
                "world.entity.projectile.hurtingprojectile.windcharge.WindCharge" to "world.entity.projectile.windcharge.WindCharge",
                "world.entity.projectile.hurtingprojectile.Fireball" to "world.entity.projectile.Fireball",
                "world.entity.projectile.throwableitemprojectile.ThrownEgg" to "world.entity.projectile.ThrownEgg",
            ).forEach { (new, old) ->
                replace("net.minecraft.$new", "net.minecraft.$old")
                replace("net/minecraft/${new.replace('.', '/')}", "net/minecraft/${old.replace('.', '/')}")
            }
        }

        // 1.21.5 renamed some mob effects and made the selected inventory slot private
        string(current.parsed < "1.21.5" && current.parsed > "1.8.9") {
            replace("MobEffects.MINING_FATIGUE", "MobEffects.DIG_SLOWDOWN")
            replace("getInventory().getSelectedSlot()", "getInventory().selected")
            replace("getInventory().getSelectedItem()", "getInventory().getSelected()")
        }

        // Mojang mappings are the official names from 26.1 onwards
        string(current.parsed >= "26.1") {
            replace("accessWidener v2 named", "accessWidener v2 official")
        }
    }
}

// Header
spotless {
    val licenseHeader = rootProject.file("HEADER")
    lineEndings = com.diffplug.spotless.LineEnding.UNIX

    java {
        licenseHeaderFile(licenseHeader)
        target("src/**/*.java")
    }

    kotlin {
        licenseHeaderFile(licenseHeader)
        target("src/**/*.kt")
    }
}
