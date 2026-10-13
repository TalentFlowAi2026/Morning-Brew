# Verification — 13 October 2026

## Checks actually completed

- Debug APK built with Gradle 8.14, AGP 8.13.1 and JDK 21.
- Fresh local Git clone: `assembleDebug lintDebug` succeeded, all 45 tasks
  executed. Used the installed SDK and dependency cache; this was not an
  empty-cache download test.
- Medium Phone emulator, Android 17 / API 37: device acceptance suite passed
  (1 test, 0 failures, 0 errors, 0 skipped). Covers all four prices at quantities
  1 through 10, decrement totals, exclusive selection, defaults, minus at 1
  remaining enabled, no-selection ordering, two valid orders, full resets,
  and quantity changes without selection keeping total 0.
- Installed APK and opened launcher activity successfully.
- Visually inspected initial and highlighted selection screenshots, warning
  Toast, and `Latte × 3 ordered` with quantity 1, total 0 and selection cleared
  visible behind it.
- Recorded a 60-second emulator video with real touch input showing warning,
  all four selections, increment, decrement, order, reset and a second order.
- Source audit: all XML parses, all 9 layout IDs referenced in Java, no Java
  string literals, all layout text and descriptions use string resources.

## Lint

0 errors, 9 warnings; no baseline or blanket suppression. Warnings concern
target SDK age (35), newer Gradle/library versions (4), a plural heuristic
for the English order message, backup configuration advice for a stateless
app, background overdraw, and no custom launcher icon. Android supplies a
generic icon. Dependencies remain pinned to the tested toolchain.

## Limits and remaining submission work

Runtime tested only on the available API 37 emulator. API 23, physical devices,
landscape, large font settings and screen readers were not separately tested.
Rotation/activity recreation resets the order as required for Week 1.
The APK is debug signed, not a Play Store release artifact.

The included Git repository has more than 8 meaningful local commits. No
GitHub destination was provided, so no remote repository was published.
The original starter was unavailable; the initial commit is a clearly labeled
new standalone starter. Personal reflections, self-grading and questions to
the trainer remain for the trainee to complete honestly.
