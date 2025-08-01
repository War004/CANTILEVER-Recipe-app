# Round Recipes 🍳

A modern Android recipe application built with Jetpack Compose and Material Design 3, featuring a clean and intuitive interface for managing and browsing recipes.

## Features

- 📱 Modern Material Design 3 UI with dynamic theming
- 🌙 Dark and light theme support
- 🗄️ Local database storage with Room
- 🔍 Recipe browsing and management
- 📋 Detailed ingredient and instruction lists
- 🎨 Material Icons Extended support
- 🚀 Built with Jetpack Compose

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with ViewModels
- **Database**: Room (SQLite)
- **Dependency Injection**: Manual DI
- **Image Loading**: Coil
- **Navigation**: Navigation 3
- **Coroutines**: Kotlin Coroutines
- **Serialization**: Kotlinx Serialization
- **Network**: Ktor Client
- **Security**: AndroidX Security Crypto

## Requirements

- **Minimum Android Version**: Android 5.0 (API level 21)
- **Target Android Version**: Android 14 (API level 34)
- **Compile SDK**: 36
- **Java Version**: 11

## Getting Started

### Prerequisites

- Android Studio Flamingo or later
- JDK 11 or higher
- Android SDK with API level 21+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/War004/CANTILEVER-Recipe-app.git
```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Build and run the project on your device or emulator

### Building the App

To build the app from command line:

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/cryptic/roundrecipes/
│   │   │   ├── database/          # Room database, entities, and DAOs
│   │   │   ├── firsttime/         # Initial app setup and data population
│   │   │   ├── screens/           # Compose UI screens
│   │   │   ├── ui/theme/          # Material Design theme configuration
│   │   │   ├── viewModel/         # ViewModels for MVVM architecture
│   │   │   └── MainActivity.kt    # Main activity
│   │   └── res/                   # Android resources
│   ├── androidTest/               # Instrumented tests
│   └── test/                      # Unit tests
```

## Key Components

- **AppDatabase**: Room database configuration with automatic data prepopulation
- **RecipeRepository**: Data access layer for recipe management
- **HomeViewModel**: Main screen view model with recipe state management
- **HomeScreen**: Primary Compose UI for recipe browsing
- **Material 3 Theming**: Dynamic color support for Android 12+

## Configuration

The app uses the following key configurations:

- **Application ID**: `com.cryptic.roundrecipes`
- **Version**: 1.0 (Version Code: 1)
- **Package**: `com.cryptic.roundrecipes`

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is part of the CANTILEVER development program.

## Support

For support and questions, please open an issue in the GitHub repository.
