# Precious Nkomose Restaurant - Android Application

The **Precious Nkomose** Android Application provides a seamless mobile dining experience for restaurant customers, featuring user authentication, interactive table reservations, menu browsing, and real-time backend synchronization with the central MySQL restaurant database and Firebase services.

---

## 🚀 Key Features

* **User Authentication & Session Management (`MainActivity` & `RegisterActivity`)**:
  * Secure user registration and login connected directly to the MySQL `customers` database.
  * Automatic session persistence via `UserSession` (`SharedPreferences`) so returning users remain signed in.
  * Form validation for email format, phone numbers, and password security.
* **Interactive Table Selection & Booking (`PickTableActivity` & `reserve_table`)**:
  * Table selection for individual seating sections (4–8 seats) or reserving the entire venue (60 capacity).
  * Date picker (restricting past dates), time picker, and guest count stepper.
  * Direct table booking insertion into the MySQL `reservations` table via Retrofit API.
* **Live Restaurant Menu (`menu`)**:
  * Live menu category browsing (**SA Mains**, **Starters**, **Desserts**, **Drinks**) synced with the MySQL `menu` database table.
* **Firebase Services Integration**:
  * Firebase SDK and `google-services.json` integration for authentication and analytics capabilities.

---

## VIDEO DEMONSTRATION
https://1drv.ms/v/c/4fd913e1c271c48d/IQDsTGJg2VzVQYVBi_CV3PMxAU_8f81wBLN127bH6hWP70E?e=nUOTTd     

---

## 🛠️ Architecture & Tech Stack

* **Language & SDK**: Kotlin, Android Target SDK 36, Min SDK 24.
* **UI Architecture**: Android Views (`AppCompatActivity`), Material Design 3 components, ConstraintLayout, and Vector Drawables.
* **Networking**: Retrofit 2.11.0 with Gson Converter for asynchronous HTTP API calls.
* **Cloud & Database Services**:
  * **Firebase**: Google Services Plugin, Firebase BoM 33.10.0, Firebase Analytics, Firebase Auth.
  * **PHP / MySQL Backend**: XAMPP local server running REST JSON API endpoints (`api/login.php`, `api/register.php`, `api/menu.php`, `api/reserve.php`).

---

## 🌐 API & Database Sync

The Android app communicates with the XAMPP PHP backend via `http://10.0.2.2/PRECIOUS-NKOMOSE-main/PRECIOUS-NKOMOSE-main/`:

| Endpoint | Method | Description | MySQL Table |
| :--- | :--- | :--- | :--- |
| `api/login.php` | `POST` | Authenticates email & password | `customers` |
| `api/register.php` | `POST` | Registers new customer account | `customers` |
| `api/menu.php` | `GET` | Fetches active restaurant menu items | `menu` |
| `api/reserve.php` | `POST` | Inserts new table reservation | `reservations` |

---

## 📂 Recent Updates & Commits

* **Firebase SDK Integration**: Added `google-services.json` and registered Firebase BoM, Analytics, and Auth.
* **Retrofit & Network API Engine**: Integrated Retrofit 2.11.0 and Gson converter with HTTP cleartext traffic support for local API testing (`10.0.2.2`).
* **PHP REST API & Session Sync**: Implemented `ApiClient`, `ApiService`, `ApiModels`, and `UserSession` for live MySQL database sync across login, registration, menu, and reservation screens.
* **Resource Assets**: Added restaurant vector logo (`precious_nkomose_logo.xml`) and menu category drawables.
