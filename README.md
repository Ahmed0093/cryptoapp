# CryptoApp

  [CryptoVideo.webm](https://github.com/user-attachments/assets/416cdccf-b272-40df-9ea5-10bc80da3b36)

## Description
The CryptoApp is a mobile application that displays the current Bitcoin and alternative cryptocurrency prices using data from the CoinPaprika API. The app fetches the latest cryptocurrency prices from the CoinPaprika API and presents them to the user in a clean and user-friendly interface. The main screen shows the current price of Bitcoin based on the user selected currency of the displayed price(s) amongst [USD,GBP, and EUR], while another screen displays the prices of various alternative cryptocurrencies also History Screen Display past prices of bitcoin in a graph. Users can refresh the data manually, and the app also updates the Bitcoin price automatically every minute.

## Libraries Used

- [Retrofit](https://github.com/square/retrofit)
- [Hilt](https://github.com/google/dagger) for Dependency Injection
- [Jetpack Compose](https://developer.android.com/compose)
- [GSON](https://github.com/google/gson)
- [JetChart](https://github.com/fracassi-marco/JetChart) to show the Prices in Graph 
- [Mockito](https://github.com/mockito/mockito-kotlin) : for mocking in unit tests
## Known Bugs

- Splash Screen are not perfect
- UI of the screen need more improvements

## Design and Architecture

1- The app follows a Domain-Driven Design (DDD) approach, with clear separation of concerns:
- **Domain**: Contains use cases that encapsulate the business logic of the app. The use cases are responsible for fetching data from the repository and providing it to the ViewModel.
- **Data**: Contains service interfaces for accessing external APIs and DataSource Repository implementation.
- **UI**: Contains the UI layer, including Jetpack Compose screens and ViewModels.



  ![img_1.png](img_1.png)

2- **Dependency Injection**: Hilt is used to manage dependencies across the app. This ensures that components like ViewModels, Repositories, and API services are provided and managed efficiently

3- **Asynchronous Operations**: Coroutines and Flow are used to handle asynchronous operations, such as network requests and data fetching. This ensures that the app remains responsive and can handle data streams efficiently.

4- **Error Handling**: The app implements comprehensive error handling using Kotlin coroutines, exception handling mechanisms, and UI states to ensure a robust user experience.
Error Handling with Exceptions and Coroutines
Try-Catch Blocks: All network requests are wrapped in try-catch blocks to catch exceptions such as IOException and HttpException.
Coroutine Exception Handler: A custom CoroutineExceptionHandler is used to handle uncaught exceptions in coroutines, logging errors and updating the UI state accordingly.

5- **Testing** : **Unit Tests** for ViewModels is considered with **UI tests** for jetpackCompose Screens as well.

## TODO
- Add code Coverage
