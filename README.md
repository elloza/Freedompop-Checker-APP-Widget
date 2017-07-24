# Freedompop-Checker-APP-Widget
An application to check the current usage of the freedompop SIM Card. It includes a widget and notifications :)

Disclaimer:

- This application is an excuse to learn Kotlin and apply best practices in Android development.
- The API employed in this project is the *"Undocumented Freedompop API" which I have found in [this repository](https://github.com/dodysw/fpopclient). All the work is based in that API so thanks a lot dodysw :)
- I will include new functionalities progressively. (I will try to use [GitFlow For Android Development](https://riggaroo.co.za/using-git-flow-for-android-development/)

### TODO List

- [ ] Configure project structure and branchs. (Dependency Injection, Libraries, MVP, Simple Anroid test, Simple Unit test...)
- [ ] Rewrite Python API to Retrofit 2 API (Basic authentication interecepter included)
- [ ] Tests Retrofit API
- [ ] Login Screen (test for sucess and fail cases)
- [ ] Main Screen (Show detailed consumption, time until next reset, button for refresh, slider for alert, time selector for periodical updates)
- [ ] Android jobs for periodically update the current consumption
- [ ] Local notification Manager for notify excess in the consumption
- [ ] Widget (Render a custom view in a this widget with revelant info, also a button for instant refresh like Lowi)
