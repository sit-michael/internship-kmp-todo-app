# Flutter to Kotlin Multiplatform (KMP) Migration Plan

This document outlines the migration plan of the Todo Example application from **Flutter** to **Kotlin Multiplatform (KMP)** using **Compose Multiplatform**.

The target repository should preserve the educational structure of the original project, maintaining:
- A `main` branch containing initial code with known bugs and missing features, marked with `//TODO(ML)` comments.
- A `done` branch with all issues fixed and features fully implemented.

---

## 1. Analysis of the Flutter Project

The Flutter project is structured using a simplified version of Clean Architecture:
- **Presentation Layer**: Widget screens and common components (`splash`, `main/tabs`, `recycle_bin`, `add_task`, `edit_task`).
- **Domain Layer**: Core models (`Task` entity), repositories interfaces (`TaskRepository`), and state management (`TaskBloc`, `TaskEvent`, `TaskState`).
- **Data Layer**: Concrete data source implementations (`HiveTaskDataSource`, `CacheTaskDataSource`) and data models (`TaskModel`).

### 1.1 State Management
- **Flutter**: BLoC pattern using `flutter_bloc` package.
- **KMP Migration Strategy**: Use standard `androidx.lifecycle.ViewModel` in the shared module (`commonMain`) emitting UI state via Kotlin `StateFlow` and handling user events via public functions or MVI-like flows. This matches the MVVM/MVI architectures native to Modern Android Development and Compose Multiplatform.

### 1.2 Local Persistence
- **Flutter**: Hive (NoSQL Key-Value storage).
- **KMP Migration Strategy**: Migrate to **Room** (now fully supporting KMP) or **SQLDelight**. Since the data model is relational/tabular (a list of Tasks), a database like Room or SQLDelight is a robust and standard selection. Alternatively, for a simple key-value store, **Multiplatform Settings** could be used. We recommend using **Room** or **SQLDelight**.

### 1.3 Dependency Injection
- **Flutter**: `get_it` Service Locator.
- **KMP Migration Strategy**: Use **Koin**, which is the most lightweight, idiomatic, and popular dependency injection framework for Kotlin Multiplatform.

### 1.4 Native Platform Features
- **Version Tracking**: Flutter uses `package_info_plus`. In KMP, expect/actual declarations or platform ViewModels can read build configs or bundle info.
- **UUID Generation**: Flutter uses a custom `GUIDGen` utility. In KMP, we can leverage Kotlin 2.0.20+ standard library `kotlin.uuid.Uuid.random().toString()` in `commonMain`.

---

## 2. Flutter "TODO" Comments & Bug Details (To Be Migrated)

To preserve the training utility of the repository for internships, the following 10 `TODO` comments and their associated bugs/features must be replicated in the `main` branch of the KMP project.

| ID | Ticket ID | Original File | Issue / Feature Description | KMP Migration & Implementation Strategy |
|:---|:---|:---|:---|:---|
| **1** | **BUGT-4321** | `lib/domain/task/entity/task.dart` | **Task doesn't appear**: Newly created tasks are hidden because `isDeleted` defaults to `true` in the model constructor. | In `Task.kt` model constructor in `commonMain`, set default value of `isDeleted` to `true` on the `main` branch. Add the comment: `//TODO(ML): (Ticket BUGT-4321) Task does't appear`. Fix it to `false` on the `done` branch. |
| **2** | **FXT-106** | `lib/presentation/common/popup_menu.dart` | **Add Favorites Option**: The popup menu on the task item list is missing the "Add/Remove Favorite" item. | Omit the "Favorite" option from the task item popup menu in the Compose layout on `main`. Add `//TODO(ML): (Ticket FXT-106) Add Favourites function and icon`. Implement it fully on `done` using appropriate callbacks. |
| **3** | **FXT-106** | `lib/presentation/common/task_tile.dart` | **Task Tile Star Icon**: Task items do not display a star icon indicating whether they are marked as a favorite. | Omit the star icon next to the task text in the task tile Compose UI on `main`. Add `//TODO(ML): (Ticket FXT-106) Add Fav icon`. On `done`, display a filled or outlined star icon based on `task.isFavorite`. |
| **4** | **BUGT-1500** | `lib/presentation/common/task_tile.dart` | **Wrong Snackbar Message**: Checking a task completed shows "Resetted Task", and unchecking it shows "Finished Task" (behavior is swapped). | In the task checkbox click handler on `main`, swap the Toast/Snackbar messages so checking is "Resetted Task" and unchecking is "Finished Task". Add comment: `//TODO(ML) : (Ticket BUGT-1500) Wrong snackbar message`. Correct the logic on `done`. |
| **5** | **BUGT-2325** | `lib/presentation/main/edit_task/edit_task_screen.dart` | **Swapped Title & Description**: When editing, Title shows description, Description shows title. Saving also swaps them. <br><br>*Note: The original Flutter `done` branch still had a bug where it saved swapped (due to `title: _descriptionController.text` and vice versa in copyWith). We must fix this in KMP.* | On `main` branch, swap UI states/controllers for title and description in the Edit Dialog, and write saving logic incorrectly so they swap. Add comment: `//TODO(ML): (Ticket BUGT-2325) switch description and title`. On `done`, display and save them correctly. |
| **6** | **FXT-106** | `lib/presentation/main/tabs_screen.dart` | **Favorites Screen**: The app is missing the third tab/page showing favorite tasks. | Omit `FavouriteTasksScreen.kt` and its navigation config from the bottom bar on `main`. Add comment: `//TODO(ML): (Ticket FXT-106) Add fav page`. Implement on `done`. |
| **7** | **FXT-4711** | `lib/presentation/main/tabs_screen.dart` | **Navigation Drawer**: Navigation drawer is missing from the main scaffold. | In the main screen layout on `main`, omit the `ModalNavigationDrawer` component. Add comment: `//TODO(ML): Add drawer (Ticket FXT-4711)`. Implement drawer on `done`. |
| **8** | **FXT-5214** | `lib/presentation/main/tabs_screen.dart` | **App Bar Title**: The top app bar lacks a title or displays a hardcoded placeholder. | Hardcode the top bar title to "Todo" (or leave empty) on `main`. Add comment: `//TODO(ML): Add AppBar title (Ticket FXT-5214)`. On `done`, bind the title dynamically to the currently active tab title. |
| **9** | **BUGT-1734** | `lib/presentation/main/tabs_screen.dart` | **Floating Action Button Color**: The FAB color does not match the primary branding color. | On `main`, use the default container color for the FloatingActionButton. Add comment: `//TODO(ML): Adjust FAB colour (Ticket BUGT-1734)`. On `done`, style it to use the primary theme color. |
| **10**| **FXT-106** | `lib/presentation/main/tabs_screen.dart` | **Favorites Tab Icon**: Bottom Navigation Bar does not contain the Favorites item. | On `main`, render only two navigation bar items (Pending, Completed). Add comment: `//TODO(ML): (Ticket FXT-106) Add fav bottombar icon`. Render all three items on `done`. |

---

## 3. Recommended KMP Architecture & Tech Stack

To transition the app smoothly while keeping standard practices, the following architecture is recommended:

```mermaid
graph TD
    subgraph Shared Module (commonMain)
        direction TB
        subgraph Presentation
            UI[Compose UI Views]
            VM[ViewModels]
        end
        subgraph Domain
            E[Task Entity]
            R[TaskRepository Interface]
        end
        subgraph Data
            RI[TaskRepositoryImpl]
            DB[Room Database / SQLDelight]
            DS[LocalTaskDataSource]
        end
    end
    
    subgraph iOS App
        iOS[SwiftUI App Entry] --> UI
    end
    
    subgraph Android App
        Android[MainActivity] --> UI
    end

    VM --> R
    RI --> R
    RI --> DS
    DS --> DB
    UI --> VM
```

### 3.1 Dependencies Config (`gradle/libs.versions.toml`)
Ensure the following KMP-compatible libraries are added:
```toml
[versions]
kotlin = "2.4.0"
composeMultiplatform = "1.11.1"
room = "2.7.0-alpha01" # Or SQLDelight
koin = "4.0.0"
lifecycle = "2.11.0-beta01"
datetime = "0.6.0"

[libraries]
androidx-room-compiler = { module = "androidx.room:room-compiler", version.ref = "room" }
androidx-room-runtime = { module = "androidx.room:room-runtime", version.ref = "room" }
koin-core = { module = "io.insert-koin:koin-core", version.ref = "koin" }
koin-compose = { module = "io.insert-koin:koin-compose", version.ref = "koin" }
kotlinx-datetime = { module = "org.jetbrains.kotlinx:kotlinx-datetime", version.ref = "datetime" }
```

### 3.2 Domain Layer (`shared/src/commonMain/kotlin/...`)
Create the Kotlin data class mapping the Flutter `Task` entity:
```kotlin
package schwarz.digits.todo.domain.model

import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Task(
    val id: String = Uuid.random().toString(),
    val title: String,
    val description: String,
    val date: Instant,
    val isDone: Boolean = false,
    val isDeleted: Boolean = false, // TODO(ML): Set to true on main branch (Ticket BUGT-4321)
    val isFavorite: Boolean = false
)
```

### 3.3 State Management (ViewModel)
Represent the BLoC state via a single Kotlin `StateFlow`:
```kotlin
data class TaskState(
    val allTasks: List<Task> = emptyList()
) {
    val pendingTasks get() = allTasks.filter { !it.isDone && !it.isDeleted }
    val completedTasks get() = allTasks.filter { it.isDone && !it.isDeleted }
    val favoriteTasks get() = allTasks.filter { it.isFavorite && !it.isDeleted }
    val removedTasks get() = allTasks.filter { it.isDeleted }
}
```

---

## 4. Migration Execution Steps

### Step 1: Initialize Git branches in the KMP Workspace
Create `main` and `done` branches in your Kotlin Multiplatform project to mirror the training workflow.
```bash
git checkout -b main
# After completing implementation on done:
git checkout -b done
```

### Step 2: Establish the Shared Core (Domain & Data)
1. Write the `Task` entity and repository interface in `commonMain`.
2. Implement local database persistence (Room/SQLDelight) in the data package.
3. Configure the Koin module for DI.

### Step 3: Implement ViewModels
Write `TaskViewModel` in the shared module containing the business logic matching the Flutter `TaskBloc` events (adding, toggling states, restoring, deleting).

### Step 4: Implement Compose Multiplatform UI
1. Create the `App.kt` entry point.
2. Build common components: `TaskTile`, `PopupMenu`, `CustomDrawer`, `TaskList`.
3. Create screen layouts: `SplashScreen`, `TabsScreen` (incorporating navigation drawer, bottom bar, and fab), and screen tabs.

### Step 5: Inject TODO comments on `main` branch
Carefully revert changes and inject the 10 TODOs as specified in Section 2, ensuring the app builds but contains the exact bugs/missing features.

### Step 6: Fix and verify on `done` branch
Ensure all TODOs are solved on the `done` branch and verify functionality on both Android and iOS targets.
