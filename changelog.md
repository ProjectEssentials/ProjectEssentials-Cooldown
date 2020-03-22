# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.14.4-1.0.5] - 2020-03-22

### Fixed
- #5. `java.lang.NoSuchMethodError` in `net.minecraft.server.MinecraftServer.getCommandManager()`

## [1.14.4-1.0.4] - 2020-03-13

### Added
- `sendMessage` in `Messaging.kt` implemented.
- Localization processing.
- Libraries added as dependency in `build.gradle`.
- Libraries added for `1.14.4` branch.

### Changed
- `CooldownCommand.kt` cleanup. Now usings new messaging API.
- `UseExperimental` annotation replaced with `OptIn` in `CooldownConfig` `CooldownAPI.getCooldownTimeLeft`.
- `sendMsg` replaced with `sendMessage`.
- Forge updated to `28.2.0` version.
- Kotlin updated to `1.3.70` version.
- dokka updated to `0.10.1`.
- kotlinx serialization updated to `0.20.0`.

### Fixed
- Incorrect permissions checking.

### Removed
- `UseExperimental` annotation from `CooldownConfig` class.
- curseforge removed from dependency repositories.
- `jitpack.io` maven repo removed from repositories in `build.gradle`.
- `kotlin.Experimental` compiler arg removed from buildscript.
- `Project Essentials` dependencies removed from buildscript.

## [1.14.4-1.0.3] - 2020-02-08

### Changed
- Uses `permissionAPIClassPath` from CoreAPI.

### Fixed
- Inconsistent version number with semver.

## [1.14.4-1.0.2.0] - 2020-01-15

### Added
- CurseForge link in `/cooldown about` command.
- German translations by [@BixelPitch](https://github.com/BixelPitch)
- `noinspections` in [build.gradle](./build.gradle) file. 

### Changed
- Updated essentials core dependency.
- Simplified cooldown commands (from `/essentials cooldown` to `/cooldown`).
- Now cooldown module not depends on Permissions module.
- Debug information now prints in debug log, not info.
- Usings optimal `JsonConfiguration` from core module.
- Simplified code for creating directory.
- Updated changelog in [update.json](./update.json).
- Updated Permissions module dependency.
- Updated gradle wrapper to `5.6.4`.
- [build.gradle](./build.gradle) file cleanup.

### Removed
- Redundant information logging.
- Java plugin from build script.
- Permissions from mandatory dependencies.

### Fixed
- Package name. (**Break compatibility**)

## [1.14.4-1.0.1.0] - 2019-10-12

### Fixed
- Fixed bug with not synchronizing cooldowns for command and command aliases.

## [1.14.4-1.0.0.0] - 2019-10-11

### Added
- Initial release.
