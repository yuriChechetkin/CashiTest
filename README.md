# CashiTest - multiplatform (Android & iOS)

---

## 📱 Demo

### Android
![Android Demo](https://media2.giphy.com/media/v1.Y2lkPTc5MGI3NjExenVrYWh1cTY5Yzg4dmJrZmlrbTZlZndlMGsyaW40MzFoaXIxYmh6MSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/QySbYQ4saxvfTOrDWn/giphy.gif)


### iOS

https://github.com/user-attachments/assets/f52c1e72-79dd-418f-9deb-b40eac2040bf



---

## 🧰 Stack used

- **Kotlin Multiplatform Mobile (KMM)** — fully cross platform Android and iOS application
- **Jetpack Compose & Compose Multiplatform** — UI Framework
- **Ktor** — network client and mocked web server
- **Firebase Firestore** — store used
- **Koin** — for Dependency Injection
- **Voyager** — for navigation
- **Mokkery / Turbine** — for unit tests

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

💡 Tests covered business logic, repositories and viewmodels using `Turbine` and `Mokkery`.

---

## 🚀 How to run?

- Just run from Android Studio with Kotlin multiplatform plugin enough
- Tests can be run also inside Android Studio 
  
<img width="465" alt="Снимок экрана 2025-05-23 в 06 42 22" src="https://github.com/user-attachments/assets/9b556b07-e4f8-4b4a-899a-2514ed941d52" />
