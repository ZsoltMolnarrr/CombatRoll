![Title](.github/title.png)

<div align="center">

<a href="">![Java 17](https://img.shields.io/badge/Java%2017-ee9258?logo=coffeescript&logoColor=ffffff&labelColor=606060&style=flat-square)</a>
<a href="">![Environment: Client & Server](https://img.shields.io/badge/environment-Client%20&%20Server-1976d2?style=flat-square)</a>
<a href="">[![Discord](https://img.shields.io/discord/973561601519149057.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2&style=flat-square)](https://discord.gg/KN9b3pjFTM)</a>

</div>

# 🧶️ Features

- Adds Combat Roll ability for players, press `R` to roll (by default)
- Adds entity attributes to improve rolling capabilities
  - `combatroll:distance` determines how far the player rolls (the default distance is 3 blocks, server configurable)
    - Example for increasing the rollign range by 3: `/give @s leather_boots{AttributeModifiers:[{AttributeName:"combatroll:distance", Name:"testing", Amount:3.0, Operation:0,Slot:"feet",UUID:[I; 66453, 79497593, -201178, -16957605]}]} 1`
  - `combatroll:recharge` determines how quickly the cooldown of the roll ability recovers (the default cooldown is 4 seconds, server configurable)
    - Example for increasing the recharge speed by +50%: `/give @s leather_leggings{AttributeModifiers:[{AttributeName:"combatroll:recharge", Name:"testing", Amount:0.5, Operation:1,Slot:"legs",UUID:[I; 66453, 79497593, -201177, -16957605]}]} 1`
  - `combatroll:count` determines how many times a player can roll before having to wait for recharging (by default a player has 1 roll)
    - Example for increasing the number of rolls by 2: `/give @s leather_helmet{AttributeModifiers:[{AttributeName:"combatroll:count", Name:"testing", Amount:2, Operation:0,Slot:"head",UUID:[I; 66453, 79497594, -201177, -16957605]}]} 1`
- Adds new enchantments related to the rolling ability
  - `Longfooted` can be enchanted on boots
    - Increases the distance of your rolls (by 1 block / level)
  - `Acrobat's Shirt` & `Acrobat's Pants` can be enchanted on chests and legs respectively 
    - Increases the recharge speed of the roll cooldown (by +10% / level)
  - `Multi-Roll` can be enchanted on helmets
    - Grants extra rolls (+1 roll / level)
- Adds an indicator (arrow) to the ingame UI (aka HUD) showing the cooldown of the roll ability and number of rolls available
- Exhaust (aka hunger) is added to the player upon rolling (server side configurable)
- Roll ability can only be used when player is: not using item, not jumping, not swimming
- High compatibility with [Better Combat](https://github.com/ZsoltMolnarrr/BetterCombat)

# 🔧 Configuration

### Fabric

Client side settings can be accessed via the [Mod Menu](https://github.com/TerraformersMC/ModMenu).

### Forge

Client side settings can be accessed in Main Menu > Mods > Combat Roll > Config.

### Server

**Server side** configuration can be found in the `config` directory, after running the game with the mod installed.

# 🔨 Using it as a modder

## Installation

Add this mod as dependency into your build.gradle file.

Repository
```groovy
repositories {
    maven {
        name = 'Modrinth'
        url = 'https://api.modrinth.com/maven'
        content {
            includeGroup 'maven.modrinth'
        }
    }
}
```

### Fabric workspace
```groovy
dependencies {
    modImplementation "maven.modrinth:combat-roll:VERSION-fabric"
}
```
In `fabric.mod.json` add a dependency to the mod:
```json
  "depends": {
    "combatroll": ">=1.0.0"
  },
```

(Substitute `VERSION` with the name of the latest release available on [Modrinth](https://modrinth.com/mod/combat-roll/versions))

### Forge workspace
```groovy
dependencies {
    implementation "maven.modrinth:combat-roll:VERSION-forge"
}
```
In `mods.toml` add a dependency to the mod:
```
modId="combatroll"
mandatory=true
versionRange="[1.0.0,)"
ordering="AFTER"
side="BOTH"
```

(Substitute `VERSION` with the name of the latest release available on [Modrinth](https://modrinth.com/mod/combat-roll/versions))
