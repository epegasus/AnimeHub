# 🏗 AnimeHub Architecture

## ⚙️ Architecture Style
- Clean Architecture (data, domain, presentation)
- MVI (Model-View-Intent) in Presentation/UI layer
- Multi-module (feature + core separation)
- Offline-first (Room as source of truth)
- Single Activity + Compose Navigation

---

## 🧠 Layers

### 1️⃣ Presentation Layer (MVI)
- Jetpack Compose screens
- State + Events + Effect
- ViewModel handles intents

---

### 2️⃣ Domain Layer
- UseCases
- Repository interfaces
- Pure business logic

---

### 3️⃣ Data Layer
- Repository implementations
- Remote + Local data sources
- DTO ↔ Entity ↔ Domain mapping

---

### 4️⃣ Core Layer
- Core utilities and shared infra (network, db, UI, design system)

---

## 📦 Modules Overview

### 🧩 app
- MainActivity
- Navigation graph (NavHost)
- DI entry point
- App bootstrap

---

### 🌐 core-network
- Retrofit setup
- OkHttp client
- API services
- Interceptors (Auth, Logging)
- DTO models

---

### 🗄 core-database
- Room DB
- DAOs
- Entities
- Migrations

---

### 🎨 core-ui
- Reusable Compose components
- Buttons, cards, loaders
- Shared UI widgets

---

### 🎨 core-designsystem
- Theme (Material 3)
- Colors
- Typography
- Shapes

---

### 🧰 core-common
- Extensions
- Dispatchers
- Constants
- Result wrappers

---

### 📊 domain
- UseCases
- Repository interfaces
- Business rules

---

### 🔄 data
- Repository implementations
- RemoteDataSource (Retrofit usage)
- LocalDataSource (Room usage)
- Mappers (DTO ↔ Entity ↔ Domain)

---

## 🚀 Feature Modules

### 🟣 feature-splash
- App startup logic
- Navigation decision

---

### 🏠 feature-home
- Anime grid (LazyVerticalGrid)
- Paging / infinite scroll
- Observe Room flow

---

### 📄 feature-details
- Anime details screen
- Favourite toggle
- Detail state handling

---

### ❤️ feature-favourites
- Favourite anime list
- Local DB query only

---

### ⚙️ feature-settings
- Theme toggle
- App preferences
- Cache clearing

---

## 🔁 Data Flow (End-to-End)

UI (Compose + MVI Intent) 

→ ViewModel 

→ UseCase (Domain) 

→ Repository (Data) 

→ RemoteDataSource (Retrofit) + LocalDataSource (Room) 

→ Room DB (Source of Truth) 

→ Flow updates 

→ UI Recompose

---

## 🧾 Key Principles
- Room = single source of truth
- Network = sync layer only
- UI observes Flow from DB
- No business logic in UI or network
- Each feature is independent

---