This is a Carpenter Calculator that works on an Android version 16 Samsung phone. It calculates the hypotenuse of a right hand triangle in feet/inches, performs arithmetic on feet-and-inch measurements, calculates material volume, and estimates tons by material type.

## Firebase App Distribution

The repository includes a manually triggered GitHub Actions workflow at `.github/workflows/firebase-app-distribution.yml`. To enable it:

1. Create a Firebase project and register the Android app with package name `com.example.righttriangle`.
2. In Firebase App Distribution, add the tester email addresses.
3. Create a service account with Firebase App Distribution Administrator access and store its JSON key as the `FIREBASE_SERVICE_ACCOUNT` GitHub Actions secret.
4. Store the Firebase Android App ID as `FIREBASE_APP_ID`.
5. Store comma-separated tester emails as `FIREBASE_TESTERS`.
6. Run **Actions > Firebase App Distribution > Run workflow**.

The workflow distributes a debug APK. A signed release build can replace it once release signing credentials are configured.
