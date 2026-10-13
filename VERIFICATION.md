# Final verification — 13 October 2026

## Implemented scope

All Morning Brew base requirements and all three stretch goals: sizes
Small/Medium/Large (×1/×1.5/×2), sugar and milk with visible per-cup pricing,
and a dedicated landscape layout. Draft persistence was added at the user's
request, overriding the PDF's instruction to defer rotation handling.
The PDF's introductions to later projects are not full specifications for
additional apps; no Pizza Palace, booking app or TrendHub is claimed here.

## Automated checks

- assembleDebug and lintDebug: BUILD SUCCESSFUL.
- connectedDebugAndroidTest on Android 17 / API 37 Medium Phone emulator:
  3 tests, 0 failures, 0 errors, 0 skipped.
- Base acceptance test covers all four drinks at quantities 1–10, increment,
  decrement, safe minimum, exclusive selection, empty selection warning,
  repeated orders and reset.
- Options test covers every combination of 4 drinks × 3 sizes × 4 extra
  combinations at quantities 1 and 2, including fractional totals and reset.
- Lifecycle test recreates and relaunches the screen, checking drink,
  quantity, size, both extras and total; checks cleared state after order.
- XML/static audit: portrait and landscape have the same 14 IDs, all used
  in Java. Every text/content description uses a string resource. XML parses.
- Java 21 daemon was previously verified with Android Studio's Java 25
  launcher. Final source was also built from a fresh Git checkout: assembleDebug and lintDebug passed with all 45 tasks executed, using installed SDK/dependency caches.

## Real emulator interaction

- Installed and opened debug APK.
- Coffee + Medium + sugar + milk, quantity 2 = 55.
- Force-stopped the app process and relaunched: all draft fields and total
  restored correctly.
- Rotated portrait and landscape: fields and total preserved.
- Landscape controls fit the tested emulator in two columns. Portrait is
  scrollable so the complete order and all options remain reachable.
- Recorded roughly one minute showing warning, options, quantity up/down,
  valid order, complete reset and a second valid order.

## Lint and limits

0 errors, 11 non-blocking warnings: pinned tool/library/target versions (6),
plural heuristics for English messages (3), backup advice (1), and a redundant
landscape container (1). No blanket suppression or baseline hides errors.
API 23, physical devices, screen readers and emulator reboot were not tested.
Persistent preferences are stored on disk, but reboot behavior is not claimed
as separately tested. The APK is debug signed, not a Play Store release.

## GitHub status

Remote: https://github.com/YahiaNasrallah/Morning-Brew
Publication was attempted but GitHub returned 403: the available saved
credential belonged to TalentFlowAi2026, which cannot write this repository.
Selecting YahiaNasrallah found no saved credential. No password or token was
requested in chat. Publish-to-GitHub.bat performs a normal, non-force push
after the owner signs in. Source history includes the standalone starter;
the trainer's own starter was never supplied.

Video duration: 60.0 seconds.
