# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 工程介绍
1. 这是一个 Android 构建的工程，我是一个 Android 开发者，想用这个项目学习并熟练掌握 Android 新技术。
2. 后续这个项目会集成 RN 和 Flutter，但是我对于这两个不太熟悉，在后续代码中是如何构建的需要详细说明。

## 功能模块介绍
1. Home 部分，基于 WanAndroid 开放的 Api，开发功能
2. OpenEyes 模块用于开眼 App 提供的Api，进行开发对应的功能
3. User 模块用于后续个人中心能力， Flutter、RN 技术会进行混用

---

## Build Commands

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew installDebug           # Build and install on connected device
./gradlew clean assembleDebug    # Clean then build
./gradlew test                   # Run unit tests (all modules)
./gradlew connectedAndroidTest   # Run instrumented tests (requires device/emulator)
./gradlew :module-home:test      # Run unit tests for a specific module
```

### Standalone Module Development
Each feature module can run independently as an app by setting `isModule=true` in `gradle.properties`. This allows developing/testing a module without the full app.

---

## Architecture Overview

### Module Dependency Graph
```
app
├── module-home        (WanAndroid API — Home tab)
├── module-community   (Community tab — blog, follow, recommend)
└── module-user        (User profile tab — Flutter/RN planned)
    └── library-openeyes      (OpenEyes API: baobab.kaiyanapp.com)
        ├── library-common    (shared utilities, routing constants, adapters)
        └── library-core      (MVVM base classes: BaseActivity, BaseFragment, BaseViewModel)
            └── library_foundation  (networking, image loading, extension functions)
```

### MVVM Base Classes (`library-core`)
- **`BaseViewModel`** — defines `loading: LiveData<Boolean>`, `error: LiveData<Boolean>`, and abstract `loadData()`. All feature ViewModels extend this.
- **`BaseActivity<VM, B>`** / **`BaseFragment<VM, B>`** — generic classes parameterized by ViewModel and ViewBinding type. They automatically observe `loading`/`error` state and call template hooks: `parseParams()` → `initViewModel()` → `initView()` → `onSubscribeUi()`.

### Routing — DRouter
Inter-module navigation uses **DRouter** (not Jetpack Navigation between modules). Routes are string constants defined in `library-common`. Example:
```kotlin
DRouter.build("/home").start(this)
```
Services implementing `IApplicationProvider` are auto-discovered by `BaseApplication` at startup via DRouter.

### Networking (`library_foundation`)
- **`HttpServiceManager`** creates `Retrofit` instances with OkHttp3 backing.
- HTTP interceptors are discovered at runtime via DRouter (loose coupling).
- **`BaseRequest`** is the template for API calls; **`OpenEyeRequest`** implements it for the OpenEyes API.
- Both RxJava3 and Coroutines are available for async work.

### Centralized Dependency Versions
All dependency versions and coordinates live in **`config.gradle`** (root). Module `build.gradle` files reference these via `rootProject.ext`. When adding a new dependency, add the version to `config.gradle` first.

**Key versions:** AGP 7.0.3, Kotlin 1.6.21, compileSdk 31, minSdk 21, Retrofit 2.8.1, OkHttp3 4.8.1, Glide 4.11.0, RxJava3 3.1.3, MMKV 1.3.9.

### Adding a New Feature Module
1. Create the module directory and `build.gradle` applying `module_config.gradle`.
2. Add it to `settings.gradle`.
3. Add a route constant to `library-common`.
4. Register an `IApplicationProvider` for any module-level initialization.
