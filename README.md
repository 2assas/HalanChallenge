# Twitter OAuth 1.0 Tweeting App
![image](https://github.com/user-attachments/assets/011d327b-7e0b-4d78-89a0-d8df7461c139)

This project demonstrates how to use OAuth 1.0 authentication to post tweets on Twitter using Twitter APIs. The application is built with a modern, clean architecture pattern, leveraging MVI (Model-View-Intent) for state management, DI (Dependency Injection) for loose coupling, and coroutines for asynchronous tasks. The app offers a simple yet modern UI with three buttons to interact with the tweet functionality: **Copy**, **Clear**, and **Post Tweet**.

## Features

- **OAuth 1.0 Authentication**: Secure authentication with Twitter's API using OAuth 1.0.
- **Post Tweets**: Authenticate the user and allow them to post tweets via the Twitter API.
- **Modern UI**: A clean, intuitive user interface built with Jetpack Compose.
- **MVI Architecture**: The application uses MVI (Model-View-Intent) to manage the app's state and UI logic.
- **Dependency Injection (DI)**: The app follows best practices using DI to separate concerns and improve testability.
- **Coroutines**: Async operations are handled using Kotlin Coroutines, ensuring smooth interactions without blocking the UI thread.
- **Clipboard**: Copy tweet text functionality.
- **Clear**: A button to clear the text input field.

## Tech Stack

- **Kotlin**: The main language for Android development.
- **Jetpack Compose**: Modern UI toolkit for building native Android apps.
- **OAuth 1.0**: For secure authentication with Twitter's API.
- **MVI (Model-View-Intent)**: A clean and scalable architecture for managing UI state.
- **Coroutines**: For asynchronous operations and background tasks.
- **Hilt**: For Dependency Injection (DI).
- **Twitter API**: For posting tweets via Twitter's RESTful API.

## UI Components

- **Tweet Input**: A text field where the user can type their tweet. It supports character counting and ensures the tweet is within Twitter's character limits.
- **Copy Button**: Copies the tweet text to the clipboard.
- **Clear Button**: Clears the tweet input field.
- **Post Tweet Button**: Posts the tweet to Twitter via the API.
- **SnackBar**: Displays status messages for post tweet success or failure.

## Setup and Installation

1. Clone the repository to your local machine:

   ```bash
   https://github.com/2assas/HalanChallenge.git
