> ## Documentation for basic use of the CooldownAPI.

### Getting Cooldown API as dependency

```groovy
repositories {
    maven { url("https://jitpack.io") }
}

dependencies {
    compile(
        group: "com.github.projectessentials",
        name: "ProjectEssentials-Cooldown",
        version: "v1.14.4-1.0.2.0"
    )
}
```

### Configuration

Like many modifications, the Cooldown API has its own modification; it stores groups of players, players, and other necessary data related to rights. The configuration is in `json` format, which is convenient for reading and understanding.

### Configuration file Location

Due to the different file structure on the server and the client of the minecraft, technically we must use different paths for the two sides.

The differences in the paths are primarily due to the different locations of the kernel of minecraft version, but for the average person, the paths will be exactly the same, i.e. like that.

    server: ./config/ProjectEssentials/cooldown.json
    client: ./config/ProjectEssentials/cooldown.json

### Configuration file structure

##### `CommandCooldowns` configuration section.

`CommandCooldowns` contains an array of command cooldowns, each element of the array (cooldown) must have the `command name` and `cooldown value`; it looks like "heal:10" where heal it command and 10 it command cooldown (in seconds).

**note:** also, if you need set common cooldown for any command, just use literals "*", "all", "any", i.e "any:5", where any random command and 5 it command cooldown (in seconds).

##### `IgnoredPlayers` configuration section.

`IgnoredPlayers` contains an array of player nicknames; Just it contain players what not get restrictions on the use of commands.

##### Just in case.

If something goes according to the cunt, and your configuration flies, just delete the configuration or take the default configuration from here.

**Default configuration**:

```json
{
  "CommandCooldowns": [
  ],
  "IgnoredPlayers": [
  ]
}
```

### API Functions / Properties

```
CooldownAPI.DEFAULT_COOLDOWN

- description: Default command cooldown (in seconds).

- note: it value be using as callback, e.g if cooldown value is invalid.
```

```
CooldownAPI.defaultCooldownLiterals

- description: Default cooldown literals. It literals what replaced cooldowns for any command in game, i.e if you set in config all=10, then all commands be with cooldown 10 sec.

- note: it not override existing cooldown for some command.
```

```
CooldownAPI.addCooldown

- description: Just add new or replace cooldown for target player and for target command.

- accepts:
    - nickname - nickname of target player. (string)
    - command - target command for cooldown (string)
```

```
CooldownAPI.removeCooldown

- description: Just remove existing cooldown from target player and target command.

- accepts:
    - nickname - nickname of target player. (string)
    - command - target command for cooldown (string)
```

```
CooldownAPI.getCooldownIsExpired

- accepts:
    - nickname - nickname of target player. (string)
    - command - target command for cooldown (string)
    - minSecondsDuration - minimal seconds diff between old command execute and new command execute. (int)

- return: true if command cooldown expired, else return false. (boolean)
```

```
CooldownAPI.getCooldownTimeLeft

- accepts:
    - nickname - nickname of target player. (string)
    - command - target command for cooldown (string)

- return: left cooldown time in seconds for command. (double)
```

```
CommandsAliases.aliases

- description: stores all aliases, just add your alias here.

- note: Where `String` - command for aliases; Where `MutableList<String>` - aliases of command.
```

### Applying aliases

When you register your command with aliases, for cooldowns you need register aliases for cooldown api, just make it:

```kotlin
CommandAliases.aliases["mycommand"] = mutableListOf("mcmd", "mycommandalias")
```

### Dependencies using by Core API.

```
    - kotlin-std lib version: 1.3.61
    - kotlinx serialization version: 0.14.0
    - forge version: 1.14.4-28.1.114
    - brigadier version: 1.0.17
    - target jvm version: 1.8
```

### If you have any questions or encounter a problem, be sure to open an issue!
