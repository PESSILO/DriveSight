[![Workflow-Hybrid PR Check](https://github.com/PESSILO/DriveSight/actions/workflows/pr_check.yml/badge.svg?branch=master)](https://github.com/PESSILO/DriveSight/actions/workflows/pr_check.yml)


GitHub Copilot Chat Assistant — README.md

# DriveSight

DriveSight is an Android driver-assistant app that listens to notifications and provides a floating widget overlay for quick in-drive interactions. It includes a NotificationListener service and a foreground floating-widget service to present an overlay and perform background/sync tasks.

> Note: Manifest and comments in the project contain Vietnamese annotations. The app requires several sensitive permissions and special service configuration for modern Android versions (Android 12+ and Android 14/15).

## Features
- Notification listening via NotificationListenerService to extract app notifications.
- Floating widget overlay (FWService) that runs as a foreground service for in-drive assistant UI.
- Foreground service handling optimized for modern Android with special-use foreground service types and properties.
- Ability to map package names to user-friendly app names via queries.

## Requirements
- Android Studio (latest stable recommended)
- Android SDK (match project compileSdk/targetSdk in build.gradle)
- A real Android device for testing (emulator may block some behaviors like notification listener or overlay)
- Min SDK: see module build.gradle (open the repository to confirm)
- Recommended testing on Android 12+ and Android 14/15 to validate foreground service behavior and special-use permissions

## Permissions (manifest highlights and why they're needed)
The app requests multiple permissions in AndroidManifest.xml. Before running, understand and grant them as appropriate.

- android.permission.BIND_NOTIFICATION_LISTENER_SERVICE
  - Required for NotificationListenerService to read incoming notifications. This is a protected permission and the user must enable the app in Notification access settings.
- android.permission.FOREGROUND_SERVICE, FOREGROUND_SERVICE_SPECIAL_USE, FOREGROUND_SERVICE_DATA_SYNC
  - Allow the app to run as a foreground service, required for persistent background tasks and for Android 12+ restrictions.
- android.permission.SYSTEM_ALERT_WINDOW
  - Required to draw overlays / floating widgets on top of other apps. User must grant "Display over other apps" in settings.
- android.permission.ACCESS_NOTIFICATION_POLICY
  - Needed if the app queries or modifies notification policy (Do Not Disturb related).
- android.permission.ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION
  - Only if the app needs location while in background (used for location-based features). Background location requires extra steps and justifications; follow Play Store policies.
- android.permission.POST_NOTIFICATIONS
  - Required for runtime notification permission on Android 13+ if the app posts notifications.

Also: the manifest includes a <queries> block to help resolve other apps on the device when mapping package names to app names.

## Installation / Build
1. Clone the repo:
   git clone https://github.com/PESSILO/DriveSight.git
2. Open the project in Android Studio.
3. Let Gradle sync and download dependencies.
4. Inspect module build.gradle to confirm minSdk and targetSdk. Update SDK platforms in SDK Manager if necessary.
5. Build and run on a device:
   - Connect a device via USB or use ADB over Wi-Fi.
   - In Android Studio choose the device and Run.

## Device setup / Runtime permissions
Many permissions must be granted manually in device settings:

1. Grant Notification access:
   - Settings → Apps & notifications → Special app access → Notification access → Enable DriveSight.

2. Allow Display over other apps:
   - Settings → Apps → Special app access → Display over other apps → Allow for DriveSight.

3. Grant runtime permissions at first launch:
   - POST_NOTIFICATIONS (Android 13+)
   - Location permissions if your build requires them (ACCESS_FINE_LOCATION and/or ACCESS_BACKGROUND_LOCATION)
   - Foreground service permission is usually declared in the manifest; ensure the app uses a Notification for foreground service.

4. If testing on Android 12+, Android may restrict certain background behaviors if app is not from Play Store. Test on a device with developer settings enabled if needed.

## Manifest / Services (what to look for)
- NotificationListener declared as .MainActivity$NotificationListener with android:permission="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE". The service includes an intent-filter action "android.service.notification.NotificationListenerService".
- Floating widget service declared as .services.FWService with:
  - android:foregroundServiceType="specialUse|dataSync"
  - android.app.PROPERTY_SPECIAL_USE_FGS_SUBTYPE property (used to classify the FGS subtype)
  - enabled="true", exported="false"
- Comments in AndroidManifest include notes about Android 12+ and Android 14/15 special handling. Review them when updating targetSdk or adapting for the Play Store.

## Development notes
- Main packages:
  - .MainActivity — main launcher activity (contains nested NotificationListener)
  - .services.FWService — floating widget/foreground service
- If you add new special-use foreground services, ensure you follow Android guidance for service types and properties and adapt to Play Store requirements.
- Keep sensitive permissions justified and request only what is necessary.

## Testing
- Test notification reading by sending notifications from other apps.
- Test overlay interaction by enabling SYSTEM_ALERT_WINDOW and opening the floating widget.
- Test foreground behavior by monitoring the persistent notification and verifying the service stays alive for intended tasks.
- Test on multiple Android versions (especially 12, 13, 14+) since rules about services, notifications, and permissions have changed.

## Contributing
- Fork the repository and create feature branches for changes.
- Open GitHub pull requests with clear description and screenshots if UI changes.
- Include testing steps and device/OS where applicable.
- Ensure any new permissions or special-use behavior is documented in the README and in-app rationale screens if you plan to publish.

## Security & Privacy
- The app accesses notifications and possibly location. Make sure you:
  - Clearly inform users why permissions are needed.
  - Minimize data collection and avoid storing or transmitting sensitive data without encryption and explicit consent.
  - Follow Play Store and local privacy laws when handling notification or location data.

## License
Specify a license for the repository (e.g., MIT, Apache-2.0). If you want me to add a LICENSE file, tell me which license to use and I can prepare it.

## Contact / Maintainers
- Repository: https://github.com/PESSILO/DriveSight
- Maintainer: PESSILO (check repository owner details)
