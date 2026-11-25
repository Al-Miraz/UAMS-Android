UAMS ANDROID APP — STATUS BAR / SAFE AREA FIX

This update is for the Android Studio project, not the UAMS website.

FIXES
1. Applies Android system-bar and display-cutout insets to the WebView content.
   - The website header should begin below the Android status bar.
   - The bottom content area stays clear of gesture navigation / 3-button navigation.
   - The screen adapts to notches and display cutouts.
2. Preserves a dark status/navigation bar appearance with light system icons.
3. Keeps keyboard resizing enabled and accounts for the on-screen keyboard inset.
4. Disables WebView overview zoom so the existing responsive website viewport is not forcibly zoomed out to fit the full page width.

PRESERVED
- Website URL: https://almiraz.page.gd/uams/
- Package: com.almiraz.uams
- JavaScript and DOM storage
- Website cookies
- Camera permission bridge for QR/barcode scanning
- Photo/file chooser
- Android print bridge
- Download and browser back handling
- Existing GitHub Actions workflow

FILES CHANGED
- app/src/main/java/com/almiraz/uams/MainActivity.java
- app/src/main/res/values/styles.xml
- app/src/main/AndroidManifest.xml

INSTALL / BUILD
1. Back up your existing GitHub repository or Android Studio project.
2. Extract this ZIP.
3. Replace the three files above in the matching project folders.
4. Commit and push the changes to the main branch of your UAMS-Android GitHub repository.
5. Open GitHub > Actions > Build UAMS Android APK.
6. Wait for the run to complete successfully, then download the UAMS-Android-debug-APK artifact.
7. Extract it and install app-debug.apk on your phone. Uninstall the previous test APK first only if Android refuses to update it because of a signing mismatch.

TEST ON PHONE
- Open the app and check that the Android clock/status icons no longer cover the UAMS website header.
- Scroll to the bottom and confirm the last page content is above the navigation/gesture bar.
- Open login and enter text into both fields; the keyboard should not cover the active field or sign-in button.
- Test the camera scanner, photo upload, print action, Back button and light/dark website theme.

IMPORTANT
- This is an Android source update ZIP, not a compiled APK.
- I could not compile the APK in this environment because Android SDK / Gradle are not installed here. Use your existing GitHub Actions workflow to build it.
- No website files or database changes are required.
