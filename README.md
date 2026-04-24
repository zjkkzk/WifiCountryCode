# WiFi Country Code

An Xposed module to modify the Wi-Fi country code on Android devices, allowing you to bypass regional restrictions on Wi-Fi channels.

## Features

- Override the Wi-Fi country code with any ISO 3166-1 alpha-2 code
- Enable/disable the hook at any time without rebooting the framework
- Quick select common country codes (US, CN, JP, GB, DE)
- Material Design 3 UI built with Jetpack Compose

## Requirements

- Android 10 (API 29) or above
- Rooted device with an Xposed framework installed (e.g., [LSPosed](https://github.com/LSPosed/LSPosed))
- Xposed API version 93+

## Installation

1. Download the latest APK from [Releases](https://github.com/mirukurusan/WifiCountryCode/releases)
2. Install the APK on your device
3. Enable the module in your Xposed framework manager
4. Select **System Framework** as the scope (the module will prompt this automatically)
5. Reboot your device

## Usage

1. Open the app and navigate to the **Configuration** tab
2. Toggle **Enable Hook** to activate the module
3. Enter a two-letter country code (e.g., `US`, `CN`, `JP`, `GB`) or use the quick select buttons
4. Tap **Save** to apply the configuration
5. Reboot for the changes to take effect

## How It Works

The module hooks into `com.android.server.wifi.WifiCountryCode` within `system_server` and overrides the following methods:

- `setTelephonyCountryCode` — intercepts the telephony country code and replaces it with the configured value
- `pickCountryCode` — returns the configured country code
- `getCountryCode` — returns the configured country code
- `getCurrentDriverCountryCode` — returns the configured country code

This effectively forces the Wi-Fi subsystem to operate under the specified country regulatory domain, unlocking Wi-Fi channels that may be restricted in your region.

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose with Material Design 3
- **Xposed Helper:** [EzXHelper](https://github.com/KyuubiRan/EzXHelper) 3.x
- **Serialization:** kotlinx-serialization-json
- **Build:** AGP 9.x, Gradle Kotlin DSL

## Author

**mirukurusan**

- GitHub: [https://github.com/mirukurusan](https://github.com/mirukurusan)
- Project: [https://github.com/mirukurusan/WifiCountryCode](https://github.com/mirukurusan/WifiCountryCode)
