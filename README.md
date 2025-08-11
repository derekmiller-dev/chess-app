# Android Chess App

An **interactive Android chess game** built with **Kotlin** and XML-based layouts.  
Features a fully playable chessboard with legal move validation, turn tracking, win detection, persistent statistics, and integrated Google AdMob ads.

## Features

- **Fully Playable Chessboard** – Tap to select and move pieces with real-time validation for all standard chess rules.
- **Turn Tracking** – Automatic switching between White and Black players with visual indicators.
- **Win Detection** – Game ends when a king is captured.
- **Rules Page** – Visual guide explaining piece movements.
- **Statistics Page** – Tracks lifetime pieces captured and games won for each side.
- **Feedback System** – In-app email integration with player rating support.
- **Persistent Storage** – Stats saved between sessions using `SharedPreferences`.
- **Google AdMob Integration** – Displays interstitial ads before gameplay.

## Tech Stack

- **Language:** Kotlin
- **Framework:** Android SDK
- **UI:** XML Layouts (`RelativeLayout`, `GridLayout`, `ConstraintLayout`)
- **Data Storage:** SharedPreferences
- **Ads:** Google Mobile Ads (AdMob)
- **IDE:** Android Studio

## Project Structure

```
.
├── AndroidManifest.xml
├── MainActivity.kt         # Main menu, navigation, ad loading
├── ChessView.kt            # Dynamic chessboard UI & click handling
├── ChessLogic.kt           # Piece movement rules & game logic
├── res/layout
│   ├── chess_directions.xml   # Rules screen
│   ├── statistics_page.xml    # Statistics screen
│   └── activity_main.xml      # Entry screen
├── res/drawable              # Chess piece images
```
### Prerequisites
- Android Studio installed
- Minimum SDK 21 (Android 5.0)
- Google AdMob account (for production ads)

### Installation
1. Clone this repository:
2. Open the project in Android Studio.
3. Build and run on an emulator or physical device.

## How It Works
- **Board Rendering**: `ChessView` dynamically generates an 8x8 `GridLayout` and assigns IDs and piece images.
- **Move Validation**: `ChessLogic` implements movement rules for each piece type (rook, knight, bishop, queen, king, pawn) including obstruction checks.
- **Statistics Tracking**: Captures and win counts are stored persistently using `SharedPreferences`.
- **Navigation**: Buttons and `Intent` transitions handle moving between the main menu, rules, and statistics pages.

## Feedback
The app includes a **SEND RATING** button that opens an email client with your chosen rating pre-filled.  
