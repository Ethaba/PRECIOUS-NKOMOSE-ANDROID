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

## 🎬 VIDEO DEMONSTRATION
https://1drv.ms/v/c/4fd913e1c271c48d/IQDsTGJg2VzVQYVBi_CV3PMxAU_8f81wBLN127bH6hWP70E?e=nUOTTd     

---

## 🛠️ Architecture & Tech Stack

* **Language & SDK**: Kotlin, Android Target SDK 36, Min SDK 24.
* **UI Architecture**: Android Views (`AppCompatActivity`), Material Design 3 components, ConstraintLayout, and Vector Drawables.
* **Networking**: Retrofit 2.11.0 with Gson Converter & OkHttpClient for asynchronous HTTP API calls.
* **Cloud & Database Services**:
  * **Firebase**: Google Services Plugin, Firebase BoM 33.10.0, Firebase Analytics, Firebase Auth.
  * **PHP / MySQL Backend**: XAMPP local server running REST JSON API endpoints (`api/login.php`, `api/register.php`, `api/menu.php`, `api/reserve.php`).

---

## 🌐 API & Database Sync

The Android app communicates with the PHP API backend via `http://10.0.2.2/PRECIOUS-NKOMOSE-main/PRECIOUS-NKOMOSE-main/` (Emulator) or `http://192.168.8.106/PRECIOUS-NKOMOSE-main/PRECIOUS-NKOMOSE-main/` (Physical Devices):

| Endpoint | Method | Description | MySQL Table |
| :--- | :--- | :--- | :--- |
| `api/login.php` | `POST` | Authenticates email & password | `customers` |
| `api/register.php` | `POST` | Registers new customer account | `customers` |
| `api/menu.php` | `GET` | Fetches active restaurant menu items | `menu` |
| `api/reserve.php` | `POST` | Inserts new table reservation | `reservations` |

---

## 📶 Wireless Debugging Guide (Android 11+)

Follow these steps to connect and run the app on a physical Android device over Wi-Fi without needing a USB cable:

### Prerequisites
* PC and Android Phone connected to the **same Wi-Fi network**.
* Phone running **Android 11 (API level 30)** or higher.

### Step-by-Step Setup
1. **Enable Developer Options on Phone:**
   * Go to **Settings** ➔ **About Phone** ➔ Tap **Build Number** 7 times until developer mode is activated.
2. **Enable Wireless Debugging:**
   * Go to **Settings** ➔ **System** ➔ **Developer Options**.
   * Turn **ON** **Wireless Debugging** (and select *Allow* on the prompt).
3. **Pair Device in Android Studio:**
   * In Android Studio, open the **Device Manager** tool window (top right or `View` ➔ `Tool Windows` ➔ `Device Manager`).
   * Click on the **Physical** tab ➔ Select **Pair using Wi-Fi**.
   * On your phone, tap **Pair device with QR code** (or **Pair device with pairing code** under Wireless Debugging settings).
   * Scan the QR code displayed in Android Studio or enter the 6-digit pairing code.
4. **Run the App:**
   * Once paired, your phone name will appear in Android Studio's target device dropdown list.
   * Click **Run** (`Shift + F10`) to deploy and debug wirelessly over Wi-Fi!

---

## ⚙️ Android Studio Run Configurations Guide

How to set up, edit, and launch custom Run Configurations in Android Studio:

### 1. Opening Run Configurations
* Click the target device dropdown at the top toolbar (next to the green **Run** ▶ button).
* Select **Edit Configurations...** (or go to `Run` ➔ `Edit Configurations...`).

### 2. Creating an Android App Run Configuration
* Click the **`+`** (Add New Configuration) icon at the top left of the dialog.
* Select **Android App**.
* Name your configuration (e.g. `Precious Nkomose - Default`).
* Set **Module**: select `:app`.

### 3. Setting Launch Options
* Under **Launch Options**:
  * **Default Activity**: Launches `MainActivity` (or default launcher activity).
  * **Specified Activity**: Allows launching specific screens directly during testing (e.g. `.home`, `.menu`, `.PickTableActivity`).
* Under **Target Device**:
  * Select **Use same device for all targets** or select **Show Select Deployment Target Dialog**.

### 4. Running the Configuration
* Click **Apply** and **OK**.
* Select your Run Configuration from the dropdown toolbar and click **Run** (`Shift + F10`) or **Debug** (`Shift + F9`).

---

## 📂 Recent Updates & Commits

* **Wireless Debugging & Run Config Documentation**: Added complete setup guides for Android Studio Wi-Fi pairing and custom run configurations.
* **Smart API Base URL & OkHttpClient**: Added 5-second connection timeouts, `OkHttpClient` connection retry policies, and smart host resolution in `ApiClient.kt`.
* **Firebase SDK Integration**: Added `google-services.json` and registered Firebase BoM, Analytics, and Auth.
* **Retrofit & Network API Engine**: Integrated Retrofit 2.11.0 and Gson converter with HTTP cleartext traffic support for local API testing (`10.0.2.2` / Wi-Fi IP).
* **PHP REST API & Session Sync**: Implemented `ApiClient`, `ApiService`, `ApiModels`, and `UserSession` for live MySQL database sync across login, registration, menu, and reservation screens.
