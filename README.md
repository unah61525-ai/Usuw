# X-SETINGS V14.1.3

**DEVELOPER: AGUNG**

Android project for a Free Fire-oriented device information and settings dashboard.

## Build APK on GitHub
1. Create a **Private** repository on GitHub.
2. Upload/push all files from this project to the repository's `main` branch.
3. Open **Actions** → **Build X-SETINGS APK** → **Run workflow**.
4. After the workflow completes, download the `X-SETINGS-V14.1.3-debug` artifact.

A debug APK is for testing. For distribution, configure a release signing key using GitHub Actions secrets; never commit keystore files or passwords.

## KEY / licensing
The included `gungtzy` key check is a **local demo only** and is not secure or device-bound. Anyone inspecting or modifying the APK could bypass it. A real one-key/one-device license requires a backend that issues and verifies signed licenses, plus a registration/revocation policy. Do not put private API secrets in the APK or repository.

## Shizuku
Shizuku API/provider dependencies and provider declaration are included. This starter UI does not yet execute privileged commands. Before adding actions, request Shizuku permission, verify service availability, explain each operation, and restrict commands to safe, reversible Android settings.

## Feature scope
The 25 entries are a mixture of live device monitoring and informational controls. Android/OEM policies may prevent apps from forcing FPS, refresh rate, Vulkan, touch-driver behavior, or killing background apps. The app intentionally does not claim CPU/GPU boost or network-buffer tweaks.

## Notes
- Target SDK 35, minimum SDK 23.
- Build uses GitHub Actions and JDK 17.
- The ROG-style panel reports actual device model, display refresh rate, battery level/temperature/charging, and memory availability.
