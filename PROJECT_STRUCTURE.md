# DriveSight Project Structure & Firebase NDK Integration

This document outlines the directory structure and collaboration workflow for the DriveSight project, integrating Android Native (C++), Kotlin, and Firebase.

## 📂 Directory Structure

```text
DriveSight/
├── app/
│   ├── google-services.json          # Firebase configuration file (App-level)
│   ├── build.gradle.kts              # App-level build script (Plugins & Dependencies)
│   └── src/
│       ├── main/
│       │   ├── cpp/                  # Native C++ Source Code (NDK Team)
│       │   │   ├── firebase_cpp_sdk/ # Firebase C++ SDK (External)
│       │   │   ├── native-lib.cpp    # JNI Bridge & Native Logic
│       │   │   └── CMakeLists.txt    # Build configuration for Native code
│       │   ├── java/com/pessilogroup/drivesight/
│       │   │   ├── ui/               # Jetpack Compose UI (UI Team)
│       │   │   │   └── components/
│       │   │   │       └── FloatingButton.kt
│       │   │   ├── core/             # Core Logic & NDK Bridges
│       │   │   │   └── NativeBridge.kt
│       │   │   └── MainActivity.kt   # App Entry Point & Firebase Init
│       │   └── AndroidManifest.xml
│       └── res/                      # Android Resources
├── gradle/
│   └── libs.versions.toml            # Centralized dependency management
├── build.gradle.kts                  # Root-level build script
└── PROJECT_STRUCTURE.md              # This file
```

## 🛠 Collaboration Workflow

### 1. NDK Team (C++)
- **Focus:** Performance-critical logic and low-level system interaction.
- **Firebase Integration:** Uses `firebase_cpp_sdk` to log native crashes and analytics directly from C++.
- **Responsibility:** Ensure `native-lib.cpp` and `CMakeLists.txt` are optimized and don't cause memory leaks.

### 2. UI Team (Jetpack Compose)
- **Focus:** Modern, reactive user interface using Jetpack Compose.
- **Workflow:** Checkout feature branches (e.g., `feat/ui/floating-button`) from `develop`.
- **Responsibility:** Implement UI components in the `ui/` package and interact with native logic via `NativeBridge.kt`.

### 3. Integration & Bridge
- **Firebase Initialization:** Initialized in `MainActivity.kt` via a JNI call to `initFirebase(activity)` to ensure coverage for both Kotlin and Native crashes.
- **Native Bridge:** All `external` function declarations are centralized to maintain a clean API between Kotlin and C++.

## 🚀 Setup Instructions
1. **Google Services:** Ensure `google-services.json` is placed in the `app/` directory.
2. **Firebase C++ SDK:** Download and extract the SDK into `app/src/main/cpp/firebase_cpp_sdk/`.
3. **NDK Build:** Sync project with Gradle files to trigger CMake configuration.
```