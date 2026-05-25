# AnimeHub

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![License: MIT](https://img.shields.io/badge/License-MIT-green?style=flat-square)](LICENSE)

A modern **anime discovery** Android app powered by the [Kitsu API](https://kitsu.docs.apiary.io/). Browse trending anime, view details, manage favourites, and browse offline with a modular **Clean Architecture** codebase.

**Author:** [Sohaib Ahmed](https://github.com/itssohaibahmed) · [Portfolio](https://itssohaibahmed.github.io)

---

## Features

- Home feed and anime discovery
- Detailed anime screens (synopsis, ratings, metadata)
- Favourites with local persistence
- Settings and theming
- Offline-friendly caching
- Splash and dashboard navigation flow

---

## Architecture

Multi-module **Clean Architecture** with feature modules and shared core layers:

```
app
├── feature-splash, feature-dashboard, feature-home
├── feature-anime-details, feature-favourites, feature-settings
├── data, domain
└── core-network, core-database, core-ui, core-design, core-common
```

| Layer | Responsibility |
|-------|----------------|
| **Presentation** | Compose UI, ViewModels, navigation |
| **Domain** | Use cases, models |
| **Data** | Repositories, Kitsu API, Room |
| **Core** | Network, DB, design system, utilities |

---

## Tech stack

- Kotlin, Coroutines, Flow
- Jetpack Compose, Material 3
- Retrofit / OkHttp (Kitsu REST API)
- Room, DataStore
- Koin (DI)
- Modular Gradle (Kotlin DSL)

---

## Getting started

### Prerequisites

- Android Studio (latest stable)
- JDK 17+
- Android SDK 34+

### Run

```bash
git clone https://github.com/itssohaibahmed/AnimeHub.git
cd AnimeHub
```

Open in Android Studio → sync Gradle → run `app`.

---

## Documentation

Extended architecture and feature notes: [`docs/README.md`](docs/README.md)

---

## Screenshots

See [`screenshots/`](screenshots/) for UI captures.

---

## License

MIT — see [LICENSE](LICENSE).
