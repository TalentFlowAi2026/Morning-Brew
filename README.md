# Morning Brew

Week 1 Android assignment implemented with **Java + XML Views**, using one
AppCompatActivity and nested LinearLayouts. App name: **Morning Brew**.
Application ID and namespace: **com.yahya.brewmorning**.

## Open in Android Studio

1. Extract the project ZIP into a writable folder.
2. Choose **Open** in Android Studio and select the `MorningBrew` directory
   containing `settings.gradle` (not just its `app` folder).
3. Allow Gradle sync, install Android SDK Platform 36 if prompted, and use
   JDK 17 or 21 as the Gradle JDK. The delivery was built with JDK 21.
4. Select the `app` configuration and an emulator/device running Android 6.0
   (API 23) or newer, then press Run.

The wrapper pins Gradle 8.14; the project pins Android Gradle Plugin 8.13.1,
compile SDK 36 and target SDK 35. Android Studio supplies the SDK location in
`local.properties`; that machine-specific file is intentionally not shipped.
Internet access is needed on the first build to download dependencies.

## Clone, build and test

The ZIP includes a local `.git` repository with the actual implementation
history. No GitHub remote was supplied or published. To make a clean clone:

```sh
git clone /path/to/extracted/MorningBrew MorningBrew-clean
cd MorningBrew-clean
```

Configure your SDK through Android Studio, `ANDROID_HOME`, or an untracked
`local.properties` containing `sdk.dir=/path/to/Android/Sdk`.

Windows:

```bat
gradlew.bat assembleDebug lintDebug
gradlew.bat connectedDebugAndroidTest
```

macOS/Linux:

```sh
./gradlew assembleDebug lintDebug
./gradlew connectedDebugAndroidTest
```

Device tests require one running emulator or connected, authorized device.
APK output: `app/build/outputs/apk/debug/app-debug.apk`.
It is a debug-signed APK suitable for installation and assessment.

## Screen behavior

- Tea 10, Coffee 15, Cappuccino 20 and Latte 25 appear in one row.
- Launch: no selection, quantity 1, total 0.
- Tapping a drink replaces the current selection. Selected buttons have a
  dark brown background and white text; unselected buttons are white.
- Plus/minus update quantity and total immediately. Minus at 1 stays enabled
  and does nothing. Plus is guarded against integer overflow; multiplication
  uses `long` to avoid price overflow.
- Place Order without a selection shows a warning Toast and keeps the state.
- A valid order shows the drink and quantity in a Toast, then clears selection,
  restores quantity 1 and immediately displays total 0.
- All visible strings, including numeric format templates, come from
  `strings.xml`. Prices are integers in Java, never literal text in the layout.
- All nine layout IDs are referenced in Java. No Compose or programmatic UI.

## Files to study or adapt into your own starter

- `app/src/main/java/com/yahya/brewmorning/MainActivity.java`
- `app/src/main/res/layout/activity_main.xml`
- `app/src/main/res/values/strings.xml`
- `app/src/main/res/values/styles.xml`
- `app/src/main/res/drawable/drink_background.xml`
- `app/src/main/res/color/drink_text.xml`

No existing Android Studio starter was provided, so this is a standalone
project. If adapting it, also match the applicationId, namespace, source
directory and manifest activity name. Modern AGP defines the manifest package
from `namespace`/`applicationId`; adding a legacy source-manifest `package`
attribute is unsupported. The merged manifest and installed app have the
required package `com.yahya.brewmorning`.

## Known Week 1 limitation

Rotation or activity recreation resets the order. This is deliberate: the
assignment explicitly postpones saved state until Week 5. No size/add-on
stretch features were added.

## Submission items requiring the trainee

Publish the included repository to your own GitHub account and share its URL.
The initial commit is a newly created standalone starter, not the unavailable
trainer starter. Supply your own baseline reflection, grade and questions to
the trainer; these personal learning claims cannot be filled in for you.
See `VERIFICATION.md` for checks actually run and delivery evidence.
