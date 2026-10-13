# Morning Brew — updated desktop project

## Android Studio fix

The screenshot showed Gradle 8.14 attempting to use Java 25. The project now
pins its Gradle daemon to Java 21 in `gradle/gradle-daemon-jvm.properties`.
Local Android Studio settings also point to the installed Temurin 21 JDK.
The fix was verified by launching the wrapper using Android Studio's Java 25:
the actual build daemon selected Java 21 and the build/tests passed.

In the currently open Android Studio window, run File > Sync Project with
Gradle Files, then choose the app run configuration and Run. If the IDE
retains its previous error, close and reopen this project and sync again.
If asked for Gradle JDK, select the installed JDK 21, not Java 25.
The desktop IDE sync itself was not operated automatically.

## Portable import and build

Open the folder containing settings.gradle. Install JDK 21 and SDK Platform
36. Gradle 8.14 and Android Gradle Plugin 8.13.1 are pinned. Java + XML Views,
package com.yahya.brewmorning, minimum API 23, target API 35.

Windows: `gradlew.bat assembleDebug lintDebug`
Device tests: `gradlew.bat connectedDebugAndroidTest`
macOS/Linux: use `./gradlew` instead.
Configure the SDK through Android Studio or ANDROID_HOME; local.properties
is machine-specific and excluded from the portable ZIP.

## Updated appearance and verification

Rounded drink cards with cup illustrations, a gradient coffee header, a
separate order summary, rounded ripple buttons and a custom vector app icon.
The original four drinks, prices, single selection, minimum quantity 1,
live total, warning Toast and successful-order reset remain intact.
All visible strings remain in strings.xml and all 9 layout IDs are used.
Rotation resets the order as required by the Week 1 assignment.

Verified on 13 October 2026: assembleDebug, lintDebug and
connectedDebugAndroidTest succeeded on the Android 17 / API 37 emulator.
One acceptance test passed with zero failures, errors or skips. It covers
all four prices at quantities 1–10, decrement totals, exclusive selection,
minimum guard, no-selection ordering, repeated valid orders and reset.
Lint: zero errors, 8 non-blocking warnings (pinned target/tool versions,
plural-message heuristic, backup advice and small cafe label).
API 23, physical devices and screen readers were not tested.

The source project on the Desktop was updated directly. Its pre-existing
staged README deletion was preserved; this guide is a separate file.
No GitHub repository was published. The APK is debug signed.
