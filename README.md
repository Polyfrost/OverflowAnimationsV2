# OverflowAnimations

[![Modrinth downloads](https://img.shields.io/modrinth/dt/4Hfmgaef?style=flat&logo=modrinth&label=Modrinth&color=1bd96a)](https://modrinth.com/mod/animations)
[![CurseForge downloads](https://img.shields.io/curseforge/dt/1147257?style=flat&logo=curseforge&label=CurseForge&color=f16436)](https://www.curseforge.com/minecraft/mc-mods/animations)

OverflowAnimations brings back animations and visuals from older versions of Minecraft, mostly 1.7 and 1.8. Every option says which version it comes from, and a lot of them let you choose between several.

You can use `/overflowanimations presets` to set everything to a 1.7, 1.8 or modern look at once (Fabric versions).

### What comes turned on

- 1.7 held item and fishing rod positions
- Swing your arm while using an item and while mining
- Swing through the delay after a missed hit
- Armor turns red together with the player wearing it
- The 1.7 blocking pose in third person
- No equip animation when you use an item

![Damaged armor](screenshots/damaged_enchanted_armor.png)

### Items

- Old enchantment glint for items, armor and potions
- 2D dropped items and item frame items, like in 1.7
- Scale, move and rotate the held item
- Custom swing speed
- Reposition the eating, drinking and blocking animations, dropped items and projectiles
- Equip animation and fishing rod position from several versions

![1.7 rod position](screenshots/old_rod_position.png)

### Players

- Sneak animation, cape movement and backwards walking from older versions
- The 1.8 hurt camera tilt
- Red damage tint on armor, held items and capes
- Dinnerbone mode for players or every entity

### Screen and HUD

- Old camera positions
- Crosshair in third person
- 1.7 or 1.8 tab list
- Status effects next to the inventory, like in 1.8
- Old tooltips, slot hover, loading screen, chat position and crafting slot (Fabric versions)

### Sky, fog and lighting

Most of these need 1.21.6 or newer.

- Old clouds, the old sky colors, void fog and void particles
- Old water fog color
- The 1.12.2 lightmap and old diffuse lighting
- Grass sides without the biome tint, and no random block rotation
- The 1.12.2 main menu panorama

### Extras

- A Fixes category for small vanilla bugs (Fabric versions)
- Three optional resource packs with 1.8 era textures, the 1.12.2 panorama and old water
- `/overflowanimations on` and `off` switch everything at once
- Press `\` to open your settings

### Servers

On the Fabric versions, a server can turn on a few old gameplay mechanics for you, like no miss delay, using items while mining and the 1.8 sneak height. They're never active in singleplayer or on servers that do not ask for them.

## Compatibility

- OverflowAnimations is based on Animatium, so servers with Animatium support work with it
- On Lunar Client the armor tint options are hidden, since Lunar tints armor itself
- With BetterHurtCam installed, BetterHurtCam controls the damage tilt

## Supported versions

- Minecraft 1.8.9 on Ornithe.
- Minecraft 1.21.4 and 1.21.6-26.3 on Fabric.

OverflowAnimations needs [OneConfig](https://modrinth.com/mod/oneconfig) and Fabric Language Kotlin. The Fabric builds also need Fabric API, and the 1.8.9 build needs Ornithe Standard Libraries.

## Support

- Report bugs and ask questions on the [Polyfrost Discord](https://polyfrost.org/discord).
- Check [Polyfrost Status](https://status.polyfrost.org/) if a Polyfrost service is down.
