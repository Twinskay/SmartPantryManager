# Smart Pantry Manager

Smart Pantry Manager is an Android application developed in Java using Android Studio. The app allows users to manage pantry ingredients and receive recipe suggestions based only on ingredients currently available in their pantry.

The application supports adding, editing, deleting, and viewing pantry ingredients. It also checks ingredient quantities and units before suggesting matching recipes.
## Database

The application uses SQLite as its local database.

SQLite was chosen because it is built into Android, works offline, and is suitable for storing structured application data without requiring an internet connection or external database server.

The database stores pantry ingredients, recipes, and recipe ingredients. Pantry data remains available after the application is closed and reopened.
## Setup and Run

1. Open the project in Android Studio.
2. Allow Gradle to finish syncing.
3. Make sure an Android emulator or physical Android device is connected.
4. Click the Run button in Android Studio.
5. The Smart Pantry Manager application will launch on the selected device.
6. ## Features

- Add pantry ingredients
- Edit pantry ingredients
- Delete pantry ingredients
- View saved pantry ingredients
- Store pantry data using SQLite
- Suggest recipes using only available pantry ingredients
- Check ingredient quantities before matching recipes
- Handle simple singular and plural ingredient names
- Check compatible ingredient units
- View recipe details and preparation steps
- Display a message when no recipes match
- Save a profile name using SharedPreferences
- Navigate between Pantry, Recipes, and Settings