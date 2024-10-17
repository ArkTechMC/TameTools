# Tame Tools

This mod is an addon for
[Tameable](https://github.com/ArkTechMC/Tameable)([CurseForge](https://www.curseforge.com/minecraft/mc-mods/tameable)|[Modrinth](https://modrinth.com/mod/tameable)),
which add some tame tools for players to tame mobs more easily.

## Currently added

### Tame Staff

There are 4 kinds of staffs. Each staff can tame mobs under their max tame mob health.

Tame will spend experience level based on the mobs' health.

This staff can tame every mobs (Except `Ender Dragon`, `Wither` and `Warden`, configurable through datapacks) without
any configurations.

### Transfer Stone

Use to transfer your pet to other players.

### Release Stone

Use to make the mob no longer your pet.

### Store Stone

You can store a pet into this item.

## Configurations

**All configurations are `/gamerule`!**

- `tame:enable_tame_staff` (Default **`TRUE`**): Enable tame staffs ability.
- `tame:tame_use_exp` (Default **`TRUE`**): Tame through staffs will spend experience level
- `tame:enable_release_stone` (Default **`TRUE`**): Enable release stone ability.
- `tame:enable_store_stone` (Default **`TRUE`**): Enable store stone ability.
- `tame:enable_transfer_stone` (Default **`TRUE`**): Enable transfer stone ability.
- `tame:enable_leash_transfer` (Default **`FALSE`**): Enable leash transfer. (Leash your pet and sneak-click another
  player)