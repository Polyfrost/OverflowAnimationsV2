import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("dev.kikugie.loom-back-compat")
    id("org.jetbrains.kotlin.jvm") version "2.4.10"
    id("com.google.devtools.ksp") version "2.3.6"
    id("dev.kikugie.fletching-table.fabric") version "0.1.0-alpha.22"
    id("dev.deftu.gradle.bloom") version "0.2.0"
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
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
    else -> JavaVersion.VERSION_1_8
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
    maven("https://maven.isxander.dev/releases") { // YACL, some releases only on Maven Central
        name = "isXander"
        content { includeGroup("dev.isxander") }
    }
    strictMaven("https://maven.bawnorton.com/releases", "Bawnorton", "com.github.bawnorton.mixinsquared")
    strictMaven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1", "DevAuth", "me.djtheredstoner")
    maven("https://maven.nucleoid.xyz/") { name = "Nucleoid" } // Placeholder API - required by Mod Menu
}

dependencies {
    minecraft("com.mojang:minecraft:$mcversion")
    loomx.applyMojangMappings()

    modImplementation("net.fabricmc:fabric-loader:$loaderversion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${sc.properties.get<String>("deps.fabric_api")}")
    modImplementation("com.terraformersmc:modmenu:${sc.properties.get<String>("deps.modmenu")}")
    modImplementation("dev.isxander:yet-another-config-lib:${sc.properties.get<String>("deps.yacl")}")
    include(modImplementation("net.fabricmc:fabric-language-kotlin:${sc.properties.get<String>("deps.fabric_language_kotlin")}")!!)

    include(implementation("com.moulberry:mixinconstraints:${sc.properties.get<String>("deps.mixinconstraints")}")!!)
    include(implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-fabric:${sc.properties.get<String>("deps.mixinsquared")}")!!)!!)

    modRuntimeOnly("me.djtheredstoner:DevAuth-fabric:${sc.properties.get<String>("deps.devauth")}")
}

loom {
    fabricModJsonPath = rootProject.file("src/main/resources/fabric.mod.json")
    accessWidenerPath = sc.process(rootProject.file("src/main/resources/$modid.accesswidener"), "build/processed.accesswidener")

    runConfigs.all {
        preferGradleTask = true
        generateRunConfig = true
        runDirectory = rootProject.file("run")
        jvmArguments.add("-Dmixin.debug.export=true")
        jvmArguments.add("-Dmixin.hotSwap=true")
        jvmArguments.add("-Ddevauth.enabled=true")
        jvmArguments.add("-Ddevauth.account=main")
    }

    runConfigs.remove(runConfigs["server"])
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
            "curseforge" to sc.properties.get<String>("mod.curseforge"),
            "discord" to sc.properties.get<String>("mod.discord"),
            "fabric_loader_version" to loaderversion,
            "minecraft_version_range" to versionrange
        )

        inputs.properties(props)

        filesMatching("fabric.mod.json") { expand(props) }
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

    modLoaders.add("fabric")

    dryRun = modrinthId == null && curseforgeId == null
    if (modrinthId != null) {
        modrinth {
            projectId = modrinthId
            accessToken = findProperty("modrinth.token").toString()
            minecraftVersions.addAll(releases)

            requires("fabric-api")
            requires("yacl")
            optional("modmenu")
        }
    }

    if (curseforgeId != null) {
        curseforge {
            projectId = curseforgeId
            projectSlug = modid
            accessToken = findProperty("curseforge.token").toString()
            minecraftVersions.addAll(releases)
            client = true

            requires("fabric-api")
            requires("yacl")
            optional("modmenu")
        }
    }

    findProperty("discord.webhook")?.toString()?.takeIf { it.isNotBlank() }?.let { url ->
        discord {
            webhookUrl = url
        }
    }
}
