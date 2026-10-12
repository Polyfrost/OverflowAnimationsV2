# OverflowAnimations

[![Modrinth downloads](https://img.shields.io/modrinth/dt/4Hfmgaef?style=flat&logo=modrinth&label=Modrinth&color=1bd96a)](https://modrinth.com/mod/animations)
[![CurseForge downloads](https://img.shields.io/curseforge/dt/1147257?style=flat&logo=curseforge&label=CurseForge&color=f16436)](https://www.curseforge.com/minecraft/mc-mods/animations)

OverflowAnimations changes how Minecraft draws held items, players, the HUD and the sky so they match an older version of the game. Each option is labeled with the version it copies, and many let you choose between 1.7, 1.8 and newer behavior.

## Features

### What a new install changes

Most options start on their vanilla value. These start on their old value:

- Held items sit at their 1.7 angle in first and third person.
- The fishing rod sits at its 1.7 position in first person.
- Your arm swings when you left-click a block while you use an item, and the block gives off breaking particles.
- Your arm swings when you click during the delay that follows a missed hit. If you aimed at an entity, the critical and sharpness particles show too.
- Armor turns red together with the player who takes damage.
- A player who blocks in third person holds the arm in its 1.7 pose.
- Using an item does not play the equip animation.

![Armor tinted red on a damaged player](screenshots/damaged_enchanted_armor.png)

### Presets

On Fabric, run `/overflowanimations presets` and pick 1.7, 1.8 or Modern. A preset sets 91 options across the Movement, Screen, Items and Other categories. Modern sets them to vanilla. A preset does not change the Fixes category or the position sliders.

### Items

- Choose the enchantment glint version for items, armor and potions separately.
- Draw dropped items and items in frames as flat sprites, as 1.7 did.
- Scale the held item between 0.2 and 2.0 on each axis, and offset or rotate it.
- Set your own swing speed, with separate speeds for Haste and Mining Fatigue.
- Move, rotate and scale the eating and drinking animation, the sword block, dropped items, thrown projectiles and fireballs. Each has its own sliders.
- Pick the equip animation from 1.7, 1.8 or vanilla, or turn it off.
- Pick the fishing rod position from 1.7, 1.8, 1.13 or 1.17 and newer.

![The fishing rod at its 1.7 position](screenshots/old_rod_position.png)

### Players and movement

- Pick the sneak animation from 1.7, 1.8, 1.13 or 1.14 and newer.
- Pick the cape movement from 1.12 and older or 1.13 and newer.
- Choose how the body turns when a player walks backwards, and whether head rotation is smoothed between server updates.
- Choose whether the camera tilts the same way on every hit, as in 1.8, or away from the damage source.
- Apply the red damage tint to armor, held items and capes, and choose its color from 1.7, 1.8 or vanilla.
- Render players, or every entity, upside down with the Dinnerbone options.

### Screen and HUD

- Pick the camera position from 1.8, 1.9 to 1.13.2, 1.14 to 1.14.3 or 1.14.4 and newer.
- Show the crosshair in third person, as 1.8 did.
- Choose the tab list style from 1.7, 1.8 or no player heads.
- Draw inventory status effects to the left of the inventory, as 1.8 did.
- On Fabric, restore the old tooltip corners, the old slot hover, the loading screen from 1.13 and older, the chat position from 1.11 and older and the crafting slot position from 1.8.

### Sky, fog and lighting

On Fabric, these options need Minecraft 1.21.6 or newer. The 1.8.9 build has only the void fog, grass and block rotation options from this list.

- Cloud rendering from before 1.21.6.
- The blue lower half of the sky, and the void fog and void particles from 1.7.
- The water fog color from before 1.13.
- The lightmap from 1.12.2 and the diffuse lighting from before 1.16.
- Grass sides without the biome tint, and blocks without random rotation.
- The main menu panorama as 1.12.2 drew it.

### Fixes

The Fabric builds have a Fixes category, and every switch in it starts on. Two examples:

- Fire charges no longer show fire that only exists on your client.
- The attack indicator hides at very high attack speeds, which fixes MC-268420.

### Resource packs

The Fabric builds add three resource packs to the resource pack screen. They stay off until you enable them.

- A pack with textures from the 1.8 era, including the old wool and the old title logo.
- A pack with the 1.12.2 panorama.
- A pack with the old water textures. It needs the "Old Water Color Effects" option.

### Commands and keys

- `/overflowanimations` opens the settings.
- `/overflowanimations on` and `/overflowanimations off` turn every feature on or off. The settings screen has the same switch.
- `/overflowanimations reload` reloads the world renderer after a change.
- `\` opens the settings and `End` reloads. You can rebind both keys.

## Options by version

The settings screen only lists the options that work on the Minecraft version you run.

- The 1.8.9 build hides the options that restore 1.8 behavior the game already has. It adds 15 options of its own, such as "Disable Hurt Camera Shake", "Block-Hit While Mining" and the 1.7 debug screen text.
- The 1.21.4 build lacks the options listed under "Sky, fog and lighting".
- "Item Swap Swing Animation" needs 1.21.11 or newer.
- "Smooth Particles" needs 26.1 or newer.

## Servers

This section applies to the Fabric builds.

A server can turn on these gameplay changes for you. They stay off in singleplayer and on servers that do not ask for them, and they reset when you disconnect.

- No delay after a missed hit.
- Using an item while you mine or hold left-click.
- Entity hitboxes that are 0.1 blocks larger when you aim at them.
- The 1.8 sneaking height of 1.65 blocks.
- Entity movement calculated on your client.
- A hidden fishing bobber while it is hooked to you.
- No sprinting while you use an item or sneak.

## Compatibility

- OverflowAnimations is based on Animatium and declares the `animatium` mod ID. It answers on Animatium's network channels, so servers built for Animatium work with it.
- On Lunar Client, the armor damage tint options are hidden because Lunar Client tints armor itself.
- With BetterHurtCam installed, the "Damage Tilt" option is locked and BetterHurtCam controls the tilt.
- With ViaFabricPlus installed, the text strikethrough fix is hidden.

## Supported versions

- Minecraft 1.8.9 on Ornithe.
- Minecraft 1.21.4, 1.21.6 to 1.21.11, 26.1 to 26.1.2, 26.2 and 26.3 on Fabric.

OverflowAnimations needs [OneConfig](https://modrinth.com/mod/oneconfig) and Fabric Language Kotlin. The Fabric builds also need Fabric API, and the 1.8.9 build needs Ornithe Standard Libraries.

## Support

- Report bugs and ask questions on the [Polyfrost Discord](https://polyfrost.org/discord).
- Check [Polyfrost Status](https://status.polyfrost.org/) if a Polyfrost service is down.

## Building

```sh
./gradlew ":26.3:build"
```

Replace `26.3` with `1.8.9`, `1.21.4`, `1.21.8`, `1.21.10`, `1.21.11`, `26.1` or `26.2`. The build needs JDK 21 and JDK 25 installed.
