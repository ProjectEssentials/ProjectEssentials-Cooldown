> ## Installation instructions.

DEPRECATED !!! See https://mairwunnx.gitbook.io/project-essentials/project-essentials-cooldown

For start the modification, you need installed Forge, it is desirable that the version matches the supported versions. You can download Forge from the [link](https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.14.4.html).
Move the downloaded mod to the `mods` folder (installation example below).

Also do not forget to install dependencies, only two types of dependencies
    - mandatory (game will not start without a mod)
    - recommended (without a mod, game can start, but I recommend using it)

Downloads: [Permissions](https://github.com/ProjectEssentials/ProjectEssentials-Permissions) · [Core](https://github.com/ProjectEssentials/ProjectEssentials-Core)

```
.
├── assets
├── config
├── libraries
├── mods (that's how it should be)
│   ├── Project Essentials Core-MOD-1.14.4-1.X.X.X.jar (mandatory)
│   ├── Project Essentials Cooldown-1.14.4-1.X.X.X.jar
│   └── Project Essentials Permissions-1.14.4-1.X.X.X.jar (recommended)
└── ...
```

Now try to start the game, go to the `mods` tab, if this modification is displayed in the `mods` tab, then the mod has been successfully installed.

### Control cooldowns via commands

```
/cooldown

OR

/cooldown version

- description: base command of cooldown api module; just send you about message.

- permission: ess.cooldown or ess.cooldown.version
```

```
/cooldown save

- description: save cooldown configuration.

- permission: ess.cooldown.save
```

```
/cooldown reload

- description: reload cooldown configuration !!!without saving.

- permission: ess.cooldown.save
```

### If you have any questions or encounter a problem, be sure to open an [issue](https://github.com/ProjectEssentials/ProjectEssentials-Cooldown/issues/new/choose)!
