# CashiTest - multiplatform (Android & iOS)

---

## 📱 Demo

### Android

https://github.com/user-attachments/assets/e06e7be2-1cef-4714-97c7-962ab014a23a




### iOS

https://github.com/user-attachments/assets/fd9be55e-83a1-4ac2-abcc-1da68f37d6f6



---

## 🧰 Stack used

- **Kotlin Multiplatform Mobile (KMM)** — fully cross platform Android and iOS application
- **Jetpack Compose & Compose Multiplatform** — UI Framework
- **Ktor** — network client and mocked web server
- **Firebase Firestore** — store used
- **Koin** — for Dependency Injection
- **Voyager** — for navigation
- **Mokkery / Turbine / kotlin.test** — for unit tests (as full crossplatform unit test frameworks)

---

## 🔗 Kotlin multiplatform and architecture

- Fully multiplatform application with all of the code reusable on both platform stored in `commonMain`
- `androidMain`, `iosMain` — platform specific implementations, used for dates only in this sample
- Clear architecture with independent layers - `data`, `domain`, `presentation`, any layer could be used as multiplatform if neccessary rfom network client to business logic and ui
- MVI architreucture with splitted logic and states into Domain and ViewState - any state (Domain/View) could be reused, tested indepdently
- Flexible DI based on feature module approach in `di` package
- Common UI for both platforms based on compose multiplatform, inculding states and navigation
- All views moved to independent `ui_components` layer to be reusable as well
- Followed SOLID principle
- Can be compiled to Web and Desktop if needed

💡 Tests covered all of the layers - business logic, repositories, mappers and viewmodels using `kotlin.test`, `Turbine` and `Mokkery` as crossplatform test frameworks. Full coverage by unit tests.

---

## 🚀 How to run?

- Just run from Android Studio with Kotlin multiplatform plugin enough
- Tests can be run also inside Android Studio 
  
<img width="465" alt="Снимок экрана 2025-05-23 в 06 42 22" src="https://github.com/user-attachments/assets/9b556b07-e4f8-4b4a-899a-2514ed941d52" />

- Or download .apk file by this [link](https://drive.google.com/file/d/1no2EO3aCl-eHZ0-Y2VxEVmcyWbaKUCZY/view?usp=sharing)
