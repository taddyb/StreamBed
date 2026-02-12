<p align="center">
  <img src="docs/images/StreamBed-StreamBed-StreamBed.png" width="50%"/>
</p>

A TUI to run hydro models

## Prerequisites

- Java 21+
- Maven 3.9+

## Build & Run

```bash
mvn compile exec:java
```

## Package as Standalone App

Build a native installer that bundles its own JRE — no Java required for end users:

```bash
# Build the fat JAR + app image
mvn package -Ppackage-app

# Output location
ls target/installer/StreamBed/
```

This produces a self-contained app image in `target/installer/StreamBed/`. To create a platform installer (`.dmg`, `.msi`, `.deb`) instead, change the `<type>` in the `package-app` profile:

| Platform | Type |
|----------|------|
| macOS app bundle | `APP_IMAGE` (default) |
| macOS .dmg | `DMG` |
| macOS .pkg | `PKG` |
| Windows .msi | `MSI` |
| Windows .exe | `EXE` |
| Linux .deb | `DEB` |
| Linux .rpm | `RPM` |

## Controls

| Key | Action |
|-----|--------|
| `j` / `↓` | Navigate down |
| `k` / `↑` | Navigate up |
| `Enter` | Run selected model |
| `Esc` / `b` | Back to dashboard |
| `q` | Quit |
| `Ctrl+C` | Force quit |
