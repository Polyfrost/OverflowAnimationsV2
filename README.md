# OverflowAnimations

The all-you-could-want legacy animations mod for modern minecraft versions. Brings back animations from the 1.7/1.8 era
and more.

Join our discord: https://polyfrost.org/discord

## License

This project is licensed under the GPL-3.0 license w/ Minecraft Linking Exception.

## Download

You can download the latest releases from Modrinth [here](https://modrinth.com/mod/overflowanimations).

## Dependencies

This mod uses [YACL](https://modrinth.com/mod/yacl) as it's config library of choice. Make sure you install the correct
version to prevent crashing.

## Notes

If you are using Lunar Client, some features will not work as intended.
If you find anything else not working, please report it on the GitHub and we will try to fix it.
Do note that, Lunar does take priority in some places for enabled/disabled settings.

## Support

Have any issues or need support? Feel free to use
our [issue tracker](https://github.com/Polyfrost/OverflowAnimationsV2/issues) to address that. If you are reporting a
crash, make sure you include information about the mods you are using and attach any relevant log files you have. If you
want to suggest features, join our [discord](https://polyfrost.org/discord)!

## Enums

### Feature

| Feature Name                     | Identifier            | Description                                                                                                       |
|----------------------------------|:----------------------|:------------------------------------------------------------------------------------------------------------------|
| All                              | all                   | Turn on all of these features.                                                                                    |
| Miss Penalty                     | miss_penalty          | Turn on/off the swing miss penalty.                                                                               |
| Left Click Item Usage            | left_click_item_usage | Turn on/off the ability to start using a item whilst holding left click.                                          |
| Mining Item Usage                | mining_item_usage     | Turn on/off the ability to start mining/break blocks whilst using a item like in 1.7.                             |
| Hide Attached Fishing Rod Bobber | hide_rod_bobber       | Turn on/off the rendering of the fishing rod bobber when attached to you in first-person.                         |
| Pick Inflation                   | pick_inflation        | Turn on/off the 0.1 pick (hitbox) inflation when attacking an entity.                                             |
| Old Sneak Height                 | old_sneak_height      | Turn on/off the old hitbox/eye height size when sneaking like it was in <=1.8.                                    |
| Client-Sided Entity Movement     | clientside_entities   | Turn on/off the entity movement calculations from the client. (In 1.7, entity movement was handled by the client) |

## Payloads

Allows the server to enable/disable server-only features that enhance gameplay.

| Identifier             | Direction | Field Name          | Field Type         | Description                                                                         |
|------------------------|-----------|:--------------------|:-------------------|:------------------------------------------------------------------------------------|
| overflowanimations:info         | Server    | Version             | Double             | Current release version of the mod.                                                 |
|                        |           | Development Version | Optional<String>   | Current dev build commit of the mod. Only provided when mod is a development build. |
| overflowanimations:config_data  | Server    | Bundle Count        | Byte               |                                                                                     |
|                        |           | Bundle Entry*       | Bundle Entry (x)^* |                                                                                     |
| overflowanimations:set_server_features | Client    | Features List       | EnumSet\<Feature\> | List of server-features the client should use currently.                            |

NOTE: As of 3.1 for now, 'overflowanimations:config_data' is never sent as its been disabled for now.

### Bundle Entry

TODO/Currently disabled.

### Config Category

| Field Name | Field Type        | Description                       |
|------------|-------------------|-----------------------------------|
| Entry      | Config Entry (x)* | Each entry of the config category |

### Config Entry

| Field Name | Field Type      | Description                                                 |
|------------|-----------------|-------------------------------------------------------------|
| Name       | String          | The id of the config field                                  |
| Type       | Enum<EntryType> | The type used to determined the type of the value provided. |
| Value      | T               | Value based on type. (Boolean, Float, Enum)                 |