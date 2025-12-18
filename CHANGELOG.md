# 3.0.0

- Support Minecraft 1.21.11
- Support Minecraft 1.21.9, 1.21.10
- Support Minecraft 1.21.6, 1.21.7, 1.21.8

# 2.0.6

- Update some translations

# 2.0.5

- Support Minecraft 1.21.4
- Fix roll recharge attribute changes not applied dynamically

# 2.0.4

- Update translations

# 2.0.3

- Fix potential HUD rendering issues

# 2.0.2

- Fix HUD widget showing while HUD is hidden #53

# 2.0.1

- (NeoForge) Fix faulty inclusion of TinyConfig, crashing alongside Better Combat mod
- (Fabric) Fix connection failure to dedicated servers
- Support Minecraft 1.21 and 1.21.1

# 1.4.0

- Add support for NeoForge (goodbye legacy Forge)
- Rewritten packet handling backbone

# 1.3.4

- Fix roll charges even when paused #45
- Update Korean translation, thanks to @smoong951

# 1.3.3
- Update to Minecraft 1.20.4
- Improve rolling speed in water

# 1.3.2

- Fix enabling/disabling enchantments #43, thanks to Mariany P.
- Improve attribute resolution (Fabric only)

# 1.3.1

- Improve roll hotkey handling #40

# 1.3.0

- Show roll keybinding label on HUD (configurable)
- Add invulnerability frames upon roll (configurable, zero by default)
- Reduce rolling distance in liquids (water and lava) #19

# 1.2.3

- (1.20.2) Fix packet handling issues preventing multiplayer, and rejoining a world in single player 
- Add japanese translation by @SAGA23456 #29

# 1.2.2

- Add italian translation by Zano1999 #26

# 1.2.1

- Fix mod version requirements

# 1.2.0

- Disable rolling when hungry

# 1.1.5

- Add Ukrainian translation, thanks to un_roman

# 1.1.4
- Disable rolling when player is immobile

# 1.1.3
- Disable rolling when player cannot move voluntarily

# 1.1.2
- Disable rolling when movement speed at zero

# 1.1.1
- Playback speed of the rolling animation scales to match server config entry: `roll_duration`
- Reduce default value of `roll_duration` (10 -> 8)
- Add Brazilian Portuguese translation, thanks to FIFTC

# 1.1.0
- Merge Acrobat enchantments. Acrobat's Shirt and Acrobat's Pant are no more, there is only a single Acrobat enchantment, that can be applied on chest or legs slots.
- Add hide cooldown indicator when no cooldown (client side configurable)
- Enchantments can be disabled via config (server side only)
- Roll ability can now be revoked from players #14
- Add some chinese translations

# 1.0.8
- Disable HUD widget in spectator mode
- Update enchant costs