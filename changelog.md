# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.15.2-1.0.1] - 2020-03-13

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

### Removed
- `UseExperimental` annotation from `CooldownConfig` class.
- curseforge removed from dependency repositories.
- `jitpack.io` maven repo removed from repositories in `build.gradle`.
- `kotlin.Experimental` compiler arg removed from buildscript.
- `Project Essentials` dependencies removed from buildscript.

## [1.15.2-1.0.0] - 2020-02-07

### Added
- Initial release.
