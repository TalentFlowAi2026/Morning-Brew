# Morning Brew

Java + XML Views cafe order app. Package: `com.yahya.brewmorning`.

## Run in Android Studio

Clone `https://github.com/YahiaNasrallah/Morning-Brew.git` or extract the ZIP.
Open the root folder containing settings.gradle and sync Gradle. Install
JDK 21 and Android SDK Platform 36. Select the app configuration and Run.
The wrapper pins Gradle 8.14, AGP 8.13.1; the daemon criteria pin Java 21.
This resolves Android Studio's bundled Java 25 incompatibility. If an open
IDE still shows its previous error, close/reopen the project and sync.
Minimum Android API 23; target 35; compile 36. Tested with the API 37 emulator.

```sh
git clone https://github.com/YahiaNasrallah/Morning-Brew.git
cd Morning-Brew
```

Windows: `gradlew.bat assembleDebug lintDebug`
macOS/Linux: `./gradlew assembleDebug lintDebug`
Device tests: `gradlew.bat connectedDebugAndroidTest` (Windows) or
`./gradlew connectedDebugAndroidTest` (macOS/Linux).
Configure your SDK using Android Studio, ANDROID_HOME or an untracked
local.properties. First builds require internet for dependencies.
APK: app/build/outputs/apk/debug/app-debug.apk.

## Features and pricing

- Tea 10, Coffee 15, Cappuccino 20, Latte 25; one highlighted selection.
- First use starts without a drink, with quantity 1, Small size and no extras.
- Small multiplies the base price by 1; Medium by 1.5; Large by 2.
- Sugar costs 2 and milk costs 3 per cup. The assignment does not specify
  add-on prices, so these are documented implementation choices.
- Total = (base price × size multiplier + selected extras) × quantity.
  Integer hundredths avoid floating-point rounding; fractional totals show
  two decimal places. No currency is invented because the task gives none.
- Minus stays enabled at 1 and safely does nothing. Large quantities use
  long arithmetic; plus cannot overflow the quantity integer.
- A warning Toast handles no selection. A valid order Toast names the drink
  and quantity, then clears drink, quantity, size, extras and saved draft.
- Dedicated landscape XML provides two columns with independent scrolling
  when necessary. Portrait has a polished coffee theme and scrollable content.
- Draft survives rotation, activity recreation, process termination and
  reopening via Bundle plus private SharedPreferences. A confirmed order
  leaves defaults for the next launch. This intentionally goes beyond the
  original Week 1 rotation limitation at the user's request.
- All UI strings are in strings.xml; every source layout ID is referenced
  in MainActivity. UI is XML, not Compose or programmatically constructed.

## Evidence and submission

See VERIFICATION.md for actual test results and SUBMISSION_CHECKLIST.md for
remaining personal trainee steps. The deliverables folder contains the
debug APK, demo recording and screenshots when packaged for delivery.
The repository includes more than 8 meaningful commits, beginning with an
empty standalone Java/XML starter. The trainer's own starter was not supplied;
this origin cannot truthfully be represented as the trainer starter.

The PDF's discussion of Pizza Palace, booking gates, databases and TrendHub
introduces later assignments; it does not provide their implementation specs.
This project completes all executable Morning Brew requirements and all three
stretch goals, with saved state added as explicitly requested.
