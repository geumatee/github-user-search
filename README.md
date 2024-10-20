# GitHub User Search App with Compose, Paging, and Hilt

This Android application demonstrates searching for GitHub users and exploring their profiles, built with modern Kotlin libraries.

## Features

- Search for GitHub users
- Paginated results for efficient data retrieval
- Detailed user profiles with login, avatar, name, follower count, and following count
- Explore user's repository list
- Open repository URLs in Chrome custom tab for seamless browsing
- Pull-to-refresh functionality for updating search results

## Technologies

- Kotlin
- Jetpack Compose
- Paging 3
- Hilt
- Coil
- Retrofit

## Prerequisites

- Android Studio
- GitHub Account with a personal access token (**insert in local.properties file as the `github_rest_api_token` property**)

## Setup

1. Clone the repository.
2. Create a personal access token on GitHub and insert it into `local.properties` as the `github_rest_api_token` property.

## Running the App

1. Open the project in Android Studio.
2. Ensure you have an internet connection.
3. Run the app on an Android device or emulator.

## Contributing

Feel free to contribute to this project by creating pull requests!

## License

This project is licensed under the MIT License.

## Disclaimer

This project adheres to GitHub's API rate limits for responsible usage. Please consider implementing additional throttling mechanisms for production applications.