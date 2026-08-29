# LokaSport - Sports Venue Booking App

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Material Design](https://img.shields.io/badge/Material_Design-757575?style=for-the-badge&logo=material-design&logoColor=white)](https://m3.material.io/)

> A native Android application leveraging Jetpack Compose to solve scheduling conflicts in modern sports ecosystems by providing real-time venue availability and seamless booking interactions.

## Project Overview
Finding and reserving sports facilities manually in urban areas often results in overlapping schedules and wasted time. **LokaSport** bridges the information gap between sports facility providers and urban sports enthusiasts. Built entirely using **Jetpack Compose** and **Kotlin**, this app is designed to provide real-time field availability through a modern, interactive, and user-friendly interface. 

The app adopts a "Modern-Minimalist" visual identity, utilizing a custom design system with Sage Green, Dark Olive, and Cream color palettes, complemented globally by the Poppins typography.

## Technical Feature Highlights & UI Walkthrough

### 1. Authentication & State Management
Managing user entry and global routing.

| Feature | Technical Implementation | Preview |
| :--- | :--- | :--- |
| **Authentication Flow** | Provides clean, responsive Login and Registration forms utilizing secure password visualization components. | <img src="https://github.com/user-attachments/assets/dca2af13-4455-47dc-89fe-5a11662de13a" width="250"><br><img src="https://github.com/user-attachments/assets/9b0d3c6b-d178-4aec-8e0f-23126d116127" width="250"> |
| **Dynamic Dashboard & State Routing** | Instead of standard `NavHost`, global navigation is handled centrally in `MainActivity` using state variables (`currentScreen`, `orderHistory`, `favoriteVenueIds`). The dashboard features a personalized greeting and interactive calendar. | <img src="https://github.com/user-attachments/assets/a044a110-b786-4e29-ad04-8c54f0c9dc3f" width="250"><br><img src="https://github.com/user-attachments/assets/48137f3b-65f9-4f1c-ae29-297f47055ca1" width="250"> |

### 2. Smart Search & Immersive Details
Empowering users to find the perfect court with detailed, transparent information.

| Feature | Technical Implementation | Preview |
| :--- | :--- | :--- |
| **Smart Sorting Algorithm** | Implements dynamic sorting logic on the Venue List. Users can filter results by `Nearest` (distance), `Highest Rated` (rating), or `Cheapest` (price per hour). | <img src="https://github.com/user-attachments/assets/8a299989-c8ce-4eb2-9358-dc3bb0d833ca" width="250"><br><img src="https://github.com/user-attachments/assets/5c4c941e-0ce2-4f7b-97a5-e39449fb3492" width="250"> |
| **Implicit Intents (Google Maps)** | The Venue Detail screen utilizes a `TabRow` layout and fires Android Implicit Intents (`geo:0,0?q=...`) to open the venue's exact coordinates directly in the Google Maps app. | <img src="https://github.com/user-attachments/assets/d282188b-7ac2-4001-ade1-cc7d416b3d69" width="250"><br><img src="https://github.com/user-attachments/assets/61967c94-7028-4473-9956-a1d99c986b91" width="250"> |
| **Interactive Gallery Overlay** | The gallery implements manual zoom interactions. Clicking an image renders it on the top layer with a blurred background (`Modifier.blur(radius = 40.dp)`) while capturing `BackHandler` events to close the image gracefully. | <img src="https://github.com/user-attachments/assets/6d7a2d42-7f67-4717-bf88-2ee453d4acfe" width="250"> |

### 3. Dynamic Booking & Transaction Processing
A robust, step-by-step process designed to prevent scheduling conflicts.

| Feature | Technical Implementation | Preview |
| :--- | :--- | :--- |
| **Dynamic Calendar & Time-Slots** | Utilizes `java.time.LocalDate` to generate a rolling 30-day calendar. Availability is simulated via `generateRandomSlots()`. Users can multi-select hours using `Set` data structures. | <img src="https://github.com/user-attachments/assets/d297e769-bdd8-459c-a036-5389d124f39a" width="250"> |
| **Order Processing & State Locking** | The Order Review screen calculates totals. Upon confirmation, a `BookingOrder` is generated, appended to the global state, and the selected slots' availability (`isAvailable`) is programmatically locked to `false` to prevent double-booking. | <img src="https://github.com/user-attachments/assets/cfcdc2f0-0e02-486a-b15c-898ee7bb7031" width="250"><br><img src="https://github.com/user-attachments/assets/d47cc982-87b5-4f2f-b7a0-a778b6dda3aa" width="250"> |

### 4. Account & Reservation Management
Giving users full control over their sports activities.

| Feature | Technical Implementation | Preview |
| :--- | :--- | :--- |
| **Booking Orders Archive** | Dedicated screen accessing the global `orderHistory` state to archive upcoming schedules. | <img src="https://github.com/user-attachments/assets/dee28188-0917-4c64-bba0-4fa0516a9cf5" width="250"> |
| **Favorites Management** | A centralized wishlist for quickly accessing frequently visited sports centers, managed via the `favoriteVenueIds` state. | <img src="https://github.com/user-attachments/assets/6b7aae80-9060-4fbe-8f80-4afd08fe5cb8" width="250"> |
| **Profile Settings** | A straightforward account hub for managing personal details and app preferences. | <img src="https://github.com/user-attachments/assets/a4bc689b-419f-490f-bf9f-81b7f451e57e" width="250"> |

## Tech Stack & Architecture
*   **UI Toolkit:** Jetpack Compose (Declarative UI with Custom MaterialTheme styling).
*   **State Management:** Centralized top-level state hoisting (`remember { mutableStateOf(...) }`) for handling data exchanges across screens.
*   **Data Modeling:** Data classes (`Venue`, `BookingOrder`, `TimeSlot`) populated with real-time simulated mock data.

---
