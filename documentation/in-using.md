> ## Documentation for basically using CooldownAPI.

## 1. For playing and running minecraft:

#### 1.1 Download Cooldown API mod module.

Visit **Cooldown API** repository on github, visit **releases** tab and download `.jar` files of latest _pre-release_ / release (**recommended**)

Releases page: https://github.com/ProjectEssentials/ProjectEssentials-Cooldown/releases


#### 1.2 Install Cooldown API modification.

The minecraft forge folder structure below will help you understand what is written below.

Also don't forget install dependency:
  - core: https://github.com/ProjectEssentials/ProjectEssentials-Core/releases
  - permissions: https://github.com/ProjectEssentials/ProjectEssentials-Permissions/releases

```
.
├── assets
├── config
├── libraries
├── mods (that's how it should be)
│   ├── Project Essentials Permissions-1.14.4-X.X.X.X.jar
│   ├── Project Essentials Cooldown-1.14.4-X.X.X.X.jar
│   └── Project Essentials Core-MOD-1.14.4-1.X.X.X.jar
└── ...
```

Place your mods and Cooldown API mods according to the structure above.

#### 1.3 Verifying mod on the correct installation.

Run the game, check the number of mods, if the list of mods contains `Project Essentials Cooldown` mod, then the mod has successfully passed the initialization of the modification.

After that, go into a single world, then try to write the `/essentials cooldown` command, if you **get an error** that you do not have permissions, then the modification works as it should.

#### 1.4 Control cooldowns via minecraft commands.

We just made the commands for you:

```
/essentials cooldown

OR

/essentials cooldown version

- description: base command of cooldown api module; just send you about message.

- permission: ess.cooldown or ess.stuff or ess.cooldown.version
```

```
/essentials cooldown save

- description: save cooldown configuration.

- permission: ess.cooldown.save or ess.stuff
```

```
/essentials cooldown reload

- description: reload cooldown configuration !!!without saving.

- permission: ess.cooldown.save or ess.stuff
```

## 2. For developing and developers:

### 2.1 Getting started with installing.

To get the Cooldown API source for development and interactions with the command cooldowns, you need to get the dependencies and get the documentation to view it in your IDE.

Installation documentation is located in the readme file or just follow the link: https://github.com/ProjectEssentials/ProjectEssentials-Cooldown#-install-using-gradle

### 2.2 Configuration.

Like many modifications, the Cooldown API has its own modification; it stores groups of players, players, and other necessary data related to rights. The configuration is in `json` format, which is convenient for reading and understanding even the **most stupid person**.

#### 2.2.1 Configuration file Location.

Due to the different file structure on the server and the client of the minecraft, technically we must use different paths for the two sides.

The differences in the paths are primarily due to the different locations of the kernel of minecraft version, but for the average person, the paths will be exactly the same, i.e. like that.

    server: ./config/ProjectEssentials/cooldown.json
    client: ./config/ProjectEssentials/cooldown.json

#### 2.2.2 Configuration file structure.

##### 2.2.2.1 `CommandCooldowns` configuration section.

`CommandCooldowns` contains an array of command cooldowns, each element of the array (cooldown) must have the `command name` and `cooldown value`; it looks like "heal:10" where heal it command and 10 it command cooldown (in seconds).

**note:** also, if you need set common cooldown for any command, just use literals "*", "all", "any", i.e "any:5", where any random command and 5 it command cooldown (in seconds).

##### 2.2.2.2 `IgnoredPlayers` configuration section.

`IgnoredPlayers` contains an array of player nicknames; Just it contain players what not get restrictions on the use of commands.

##### 2.2.2.3 Just in case.

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

### 2.3 API usage.

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

#### 2.3.1 Applying aliases.

When you register your command with aliases, for cooldowns you need register aliases for cooldown api, just make it:

```kotlin
CommandAliases.aliases["mycommand"] = mutableListOf("mcmd", "mycommandalias")
```

### For all questions, be sure to write issues!