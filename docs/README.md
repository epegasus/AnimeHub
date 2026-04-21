# Smart Task Manager

A modern Android productivity app with cloud sync, notifications, analytics, and offline-first support.

## Project Overview

Smart Task Manager is a showcase-ready Android application designed to demonstrate end-to-end Android engineering skills in one project.  
It combines clean architecture, modularization, offline-first data handling, cloud integration, and production-style tooling.

### Core Features

- Task management (CRUD)
- Categories and tags
- Offline-first local storage
- Cloud sync
- Push notifications
- Image attachments for tasks
- Remote-configurable settings
- Analytics dashboard

## Architecture

This project follows **Clean Architecture** with a **multi-module** setup and uses **MVVM (or MVI)** at the presentation layer.

### High-Level Layers

- `presentation` - UI and state management (Jetpack Compose, ViewModel)
- `domain` - business rules, use cases, and pure models
- `data` - repositories, local/remote data sources, DTO mapping

### Suggested Architecture Diagram

Add your architecture image in `docs/architecture.md` or `screenshots/` and reference it here:

```text
[Compose UI] -> [ViewModel] -> [Use Cases] -> [Repository]
                                       |            |
                                       v            v
                                  [Room DB]   [Retrofit/Firebase]
```

## Tech Stack

### Core Android

- Kotlin
- Android Jetpack
- Jetpack Compose
- Material 3

### Architecture and Patterns

- MVVM or MVI
- Clean Architecture
- Multi-module architecture

### Dependency Injection

- Hilt

### Networking and Serialization

- Retrofit
- OkHttp
- Gson or Kotlinx Serialization

### Async and Reactive

- Kotlin Coroutines
- Kotlin Flow

### Database and Pagination

- Room
- Paging 3

### Firebase Services

- Firebase Auth
- Firebase Crashlytics
- Firebase Remote Config
- Firebase Cloud Messaging

### Image Handling

- Coil or Glide

### Testing

- JUnit
- Mockito
- Espresso
- Robolectric

### CI/CD

- GitHub Actions

## Module Structure

```text
android-showcase-app
│
├── app
├── core-ui
├── core-network
├── core-database
├── data
├── domain
├── feature-auth
├── feature-tasks
├── feature-dashboard
├── feature-settings
│
├── docs
│   ├── architecture.md
│   ├── modularization.md
│
├── screenshots
└── README.md
```

## Screenshots

Add app screenshots to the `screenshots/` folder and link them here:

```markdown
![Home Screen](screenshots/home.png)
![Task Details](screenshots/task-details.png)
![Dashboard](screenshots/dashboard.png)
```

## CI/CD Pipeline

The GitHub Actions pipeline should include:

- Build project
- Run unit tests
- Run lint checks
- Generate APK artifacts

Add your workflow badge once available:

```markdown
![CI](https://github.com/<your-username>/<repo-name>/actions/workflows/android-ci.yml/badge.svg)
```

## Advanced Features

To make this project stand out for recruiters:

- Offline-first architecture
- Pagination with Paging 3
- Dynamic theming
- Feature flags via Remote Config
- Push notifications
- File/image upload
- Dark mode
- Tablet support

## Demo Video

Record a short walkthrough (2-5 minutes) and add the link here:

- Demo: `<add-demo-link>`

## Getting Started

### Prerequisites

- Android Studio (latest stable)
- JDK 17+
- Android SDK
- Firebase project (for Auth/FCM/Remote Config/Crashlytics)

### Setup

1. Clone the repository.
2. Open the project in Android Studio.
3. Add Firebase configuration (`google-services.json`) to `app/`.
4. Sync Gradle and run the app.

## Roadmap

- [ ] Authentication flow
- [ ] Task CRUD with Room
- [ ] Cloud sync
- [ ] Push notifications
- [ ] Dashboard analytics
- [ ] Tablet layout optimization

## Contributing

Contributions are welcome. Please open an issue first to discuss major changes.

## License

This project is available under the MIT License (or your preferred license).
