UAMS ANDROID APP - SOURCE PROJECT

This is an Android WebView wrapper for the existing live UAMS website:
https://almiraz.page.gd/uams/

It uses the existing server and database. It does not bundle database credentials or local asset records.

FEATURES
- Android launcher application named "UAMS by AL MIRAZ"
- Opens the live UAMS website in an app window
- Keeps website session cookies during app use
- Supports website camera permission prompts (for QR/barcode scanning)
- Supports file picker upload (for asset photo attachment)
- Supports browser back navigation and downloads

IMPORTANT LIMITATIONS
- This is Android Studio source, NOT a precompiled APK.
- Web Push notifications inside Android WebView are not implemented by this wrapper. A native Firebase Cloud Messaging bridge or a verified Trusted Web Activity/PWA push setup is required.
- Digital signatures and PDF creation must be implemented by the website pages; this wrapper exposes the web UI and camera/file selection.
- The website must remain available over HTTPS.
- Confirm the START_URL in app/src/main/java/com/almiraz/uams/MainActivity.java if your production URL changes.

BUILD APK WITHOUT CODING (GitHub Actions)
1. Create a GitHub account and a new empty repository named UAMS-Android.
2. Upload all files and folders from this project ZIP to the repository root.
3. Open the repository's Actions tab.
4. Select "Build UAMS Android APK" and press "Run workflow", or push to main.
5. When the workflow completes, open the run and download the artifact named "UAMS-Android-debug-APK".
6. Extract the artifact and install app-debug.apk on your Android phone. Android may ask you to allow installation from that browser/file manager.

BUILD WITH ANDROID STUDIO
Open this folder as a project in Android Studio, allow Gradle sync, then choose Build > Build Bundle(s) / APK(s) > Build APK(s).

This debug APK is suitable for direct testing/sideloading. A Play Store release should use a properly protected signing key and a release build.
