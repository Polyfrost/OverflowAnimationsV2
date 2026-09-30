import net.ornithemc.ploceus.api.PloceusGradleExtensionApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("dev.kikugie.loom-back-compat")
    id("org.jetbrains.kotlin.jvm") version "2.4.10"
    id("com.google.devtools.ksp") version "2.3.6"
    id("dev.kikugie.fletching-table.fabric") version "0.1.0-alpha.22"
    id("dev.deftu.gradle.bloom") version "0.2.0"
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
    id("net.fabricmc.fabric-loom-remap") version "1.17-SNAPSHOT" apply false
    id("ploceus") version "1.17.4" apply false
}

val isOrnithe = sc.current.version == "1.8.9"
val ploceus = if (isOrnithe) {
    pluginManager.apply("net.fabricmc.fabric-loom-remap")
    pluginManager.apply("ploceus")

    configurations.configureEach {
        exclude(group = "org.lwjgl.lwjgl")
    }

    extensions.getByType<PloceusGradleExtensionApi>().apply {
        setIntermediaryGeneration(2)
    }
} else {
    null
}

val modid: String = sc.properties["mod.id"]
val modname: String = sc.properties["mod.name"]
val modversion: String = sc.properties["mod.version"]
val development: Boolean = sc.properties["mod.development"]
val mcversion: String = sc.properties.getOrNull<String>("deps.minecraft") ?: sc.current.version
val versionrange: String = sc.properties["mod.mc_compat"]
val loaderversion: String = sc.properties["deps.fabric_loader"]

version = "$modversion+$mcversion-fabric" + (if (development) "_development" else "")
group = sc.properties.get<String>("mod.group")
base.archivesName = modid

val requiredJava: JavaVersion = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    sc.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    sc.current.parsed >= "1.17" -> JavaVersion.VERSION_16
    else -> JavaVersion.VERSION_25
}

val compatibleVersions: List<String> = sc.properties.rawOrNull("mod", "mc_releases")
    ?.asList().orEmpty().map { it.toString() }

repositories {
    fun strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
        forRepository { maven(url) { name = alias } }
        filter { groups.forEach(::includeGroup) }
    }

    mavenCentral()
    strictMaven("https://maven.fabricmc.net/", "FabricMC", "net.fabricmc", "net.fabricmc.fabric-api")
    strictMaven("https://maven.terraformersmc.com/", "TerraformersMC", "com.terraformersmc")
    google()
    maven("https://repo.polyfrost.org/releases") { name = "Polyfrost Releases" }
    maven("https://repo.polyfrost.org/snapshots") { name = "Polyfrost Snapshots" }
    strictMaven("https://maven.cloverclient.com/releases", "CloverClient", "pl.tomgirl")
    strictMaven("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth")
    strictMaven("https://maven.bawnorton.com/releases", "Bawnorton", "com.github.bawnorton.mixinsquared")
    strictMaven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1", "DevAuth", "me.djtheredstoner")
}

dependencies {
    minecraft("com.mojang:minecraft:$mcversion")
    if (isOrnithe) {
        mappings(ploceus!!.layeredMappings {
            mappings("net.ornithemc:feather-gen2:$mcversion+build.${sc.properties.get<String>("deps.feather")}:v2") {
                containsUnpick()
            }
        })
        ploceus.dependOsl(sc.properties.get<String>("deps.osl"))
    } else {
        loomx.applyMojangMappings()
    }

    modImplementation("net.fabricmc:fabric-loader:$loaderversion")
    if (!isOrnithe) modImplementation("net.fabricmc.fabric-api:fabric-api:${sc.properties.get<String>("deps.fabric_api")}")
    val oneconfig: String = sc.properties["deps.oneconfig"]
    modImplementation("org.polyfrost.oneconfig:${sc.properties.getOrNull<String>("deps.oneconfig_platform") ?: sc.current.version}-${if (isOrnithe) "ornithe" else "fabric"}:$oneconfig")
    for (module in arrayOf("config", "config-impl", "internal", "ui", "utils")) {
        implementation("org.polyfrost.oneconfig:$module:$oneconfig")
    }
    include(modImplementation("net.fabricmc:fabric-language-kotlin:${sc.properties.get<String>("deps.fabric_language_kotlin")}")!!)

    compileOnly("org.jspecify:jspecify:1.0.0") // Bundled by Minecraft from 1.21.11

    if (!isOrnithe) {
        include(implementation("com.moulberry:mixinconstraints:${sc.properties.get<String>("deps.mixinconstraints")}")!!)
        include(implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-fabric:${sc.properties.get<String>("deps.mixinsquared")}")!!)!!)

        modRuntimeOnly("me.djtheredstoner:DevAuth-fabric:${sc.properties.get<String>("deps.devauth")}")
    }
}

sourceSets.main {
    for (sources in listOf(java, kotlin)) {
        if (isOrnithe) {
            sources.include(
                "org/polyfrost/overflowanimations/config/**",
                "org/polyfrost/overflowanimations/handler/config/**",
                "org/polyfrost/overflowanimations/handler/compatibility/Mods.kt",
                "org/polyfrost/overflowanimations/util/enums/**",
                "org/polyfrost/overflowanimations/OverflowAnimationsConstants.kt",
                "org/polyfrost/overflowanimations/legacy/**",
                "org/polyfrost/overflowanimations/mixins/legacy/**",
            )
        } else {
            sources.exclude("org/polyfrost/overflowanimations/legacy/**", "org/polyfrost/overflowanimations/mixins/legacy/**")
        }
    }
}

loom {
    fabricModJsonPath = rootProject.file("src/main/resources/fabric.mod.json")
    if (!isOrnithe) accessWidenerPath = sc.process(rootProject.file("src/main/resources/$modid.accesswidener"), "build/processed.accesswidener")

    runConfigs.all {
        preferGradleTask = true
        generateRunConfig = true
        runDirectory = rootProject.file("run")
        jvmArguments.add("-Dmixin.debug.export=true")
        jvmArguments.add("-Dmixin.hotSwap=true")
        jvmArguments.add("-Ddevauth.enabled=true")
        jvmArguments.add("-Ddevauth.account=main")
        if (isOrnithe && System.getProperty("os.name").startsWith("Mac")) jvmArguments.add("-XstartOnFirstThread")
    }

    runConfigs.remove(runConfigs["server"])
}

val legacyPackages = listOf("org.polyfrost.overflowanimations.legacy.", "org.polyfrost.overflowanimations.mixins.legacy.")
tasks.matching { it.name == "kspKotlin" }.configureEach {
    val generated = layout.buildDirectory.dir("generated/ksp/main/resources")
    doLast {
        generated.get().asFileTree.matching { include("fletching-table.*.json") }.forEach { file ->
            @Suppress("UNCHECKED_CAST")
            val entries = groovy.json.JsonSlurper().parse(file) as List<Map<String, Any?>>
            val kept = entries.filter { entry -> entry.values.any { it is String && legacyPackages.any(it::startsWith) } == isOrnithe }
            file.writeText(groovy.json.JsonOutput.toJson(kept))
        }
    }
}

fletchingTable {
    mixins.create("main") {
        // Every @Mixin class compiled for this node is registered, so version-gated mixins only need a Stonecutter condition
        mixin("default", "$modid.mixins.json") {
            env("CLIENT")
        }
    }

    lang.create("main") {
        patterns.add("assets/$modid/lang/**")
    }
}

java {
    withSourcesJar()
    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava

    toolchain {
        vendor = JvmVendorSpec.ADOPTIUM
        languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = requiredJava.majorVersion.toInt()
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget = JvmTarget.fromTarget(requiredJava.majorVersion)
}

val currentCommitHash: String by lazy {
    providers.exec {
        commandLine("git", "rev-parse", "--verify", "--short", "HEAD")
        isIgnoreExitValue = true
    }.standardOutput.asText.get().trim()
}

bloom {
    replacement("@MODID@", modid)
    replacement("@VERSION@", modversion)
    replacement("@DEVELOPMENT@", development.toString())
    replacement("@COMMIT@", currentCommitHash)
}

tasks {
    processResources {
        val props = mapOf(
            "id" to modid,
            "name" to modname,
            "version" to modversion,
            "description" to sc.properties.get<String>("mod.description"),
            "source" to sc.properties.get<String>("mod.source"),
            "issues" to sc.properties.get<String>("mod.issues"),
            "license" to sc.properties.get<String>("mod.license"),
            "modrinth" to sc.properties.get<String>("mod.modrinth"),
            "discord" to sc.properties.get<String>("mod.discord"),
            "fabric_loader_version" to loaderversion,
            "minecraft_version_range" to versionrange
        )

        inputs.properties(props)

        filesMatching("fabric.mod.json") {
            expand(props)
            if (isOrnithe) filter { line -> if ("\"accessWidener\"" in line || "\"fabric-" in line && "kotlin" !in line) "" else line }
        }

        if (isOrnithe) exclude("$modid.accesswidener", "resourcepacks/**", "assets/$modid/shaders/**", "assets/$modid/items/**", "assets/$modid/models/**")

        // The custom renderer is compiled out before 1.21.6, and older shader loaders choke on its includes
        if (sc.current.parsed < "1.21.6" && !isOrnithe) exclude("assets/$modid/shaders/**")

        // The player head special model was split out of "head" in 1.21.5
        if (sc.current.parsed < "1.21.5") filesMatching("**/items/player_skull.json") {
            filter { it.replace("\"type\": \"minecraft:player_head\"", "\"type\": \"minecraft:head\", \"kind\": \"player\"") }
        }
    }

    jar {
        inputs.property("archivesName", base.archivesName)

        from(rootProject.file("LICENSE")) {
            rename { "${it}_${inputs.properties["archivesName"]}" }
        }
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds mod jars and copies results to `build/libs/{mod version}/`"

        inputs.property("version", modversion)
        from(loomx.modJar.flatMap { it.archiveFile }, loomx.modSourcesJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/$modversion"))
    }
}

val modrinthId = findProperty("publish.modrinth")?.toString()?.takeIf { it.isNotBlank() }
val curseforgeId = findProperty("publish.curseforge")?.toString()?.takeIf { it.isNotBlank() }

// Tokens go in the user Gradle gradle.properties file (e.g. ~/.gradle/gradle.properties):
// modrinth.token=
// curseforge.token=
// discord.webhook=
publishMods {
    file = loomx.modJar.flatMap { it.archiveFile }

    val releases = compatibleVersions.ifEmpty { listOf(mcversion) }
    displayName = "Release $modversion for " + if (releases.size > 1) "${releases.first()}-${releases.last()}" else releases.first()
    version = modversion
    changelog = rootProject.file("CHANGELOG.md").takeIf { it.exists() }?.readText() ?: "No changelog provided."
    type = STABLE

    modLoaders.add(if (isOrnithe) "ornithe" else "fabric")

    dryRun = modrinthId == null && curseforgeId == null
    if (modrinthId != null) {
        modrinth {
            projectId = modrinthId
            accessToken = findProperty("modrinth.token").toString()
            minecraftVersions.addAll(releases)

            if (!isOrnithe) requires("fabric-api")
            requires("oneconfig")
        }
    }

    if (curseforgeId != null) {
        curseforge {
            projectId = curseforgeId
            projectSlug = modid
            accessToken = findProperty("curseforge.token").toString()
            minecraftVersions.addAll(releases)
            client = true

            if (!isOrnithe) requires("fabric-api")
            requires("oneconfig")
        }
    }

    findProperty("discord.webhook")?.toString()?.takeIf { it.isNotBlank() }?.let { url ->
        discord {
            webhookUrl = url
        }
    }
}
